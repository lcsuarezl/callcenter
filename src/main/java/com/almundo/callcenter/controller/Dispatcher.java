package com.almundo.callcenter.controller;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.almundo.callcenter.model.call.Call;
import com.almundo.callcenter.model.employee.Employee;
import com.almundo.callcenter.model.employee.Operator;
import com.almundo.callcenter.objectpool.OperatorPool;
import com.almundo.callcenter.queue.CallQueue;

public class Dispatcher implements Runnable {

	private static final Logger log = LogManager.getLogger(Dispatcher.class);
	
	private LinkedBlockingQueue<Call> waitingCalls;
	
	private LinkedBlockingQueue<Call> processedCalls;

	public Dispatcher() {
		waitingCalls = new LinkedBlockingQueue<Call>();
		processedCalls = new LinkedBlockingQueue<Call>();
	}

	/**
	 * Assigns call to employee
	 */
	public void processCallQueues() {
		Call call;
		if (waitingCalls.size() > 0) {
			call = waitingCalls.poll();
			log.info("Processing waiting call"+call.toString());
		} else {
			call = CallQueue.getInstance().getCall();
			log.info("Processing queue call"+call.toString());
		}
		Operator operator = (Operator) OperatorPool.getInstance().getEmployee();
		if (operator != null) {
			dispatchCall(call, operator);
		} else {
			waitingCalls.add(call);
		}
	}

	public void dispatchCall(Call call, Employee employee) {
		employee.answerCall(call);
		processedCalls.add(call);
	}

	private boolean pendingCall() {
		int size = CallQueue.getInstance().getSize() + waitingCalls.size();
		return size > 0;
	}

	public LinkedBlockingQueue<Call> getProcessedCalls() {
		return processedCalls;
	}

	@Override
	public void run() {
		log.info("Start run");
		while (pendingCall()) {
			processCallQueues();
		}
		log.info("End run");
	}

	
	
}
