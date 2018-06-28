package com.almundo.callcenter.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.exception.CallCenterException;
import com.almundo.callcenter.model.call.Call;
import com.almundo.callcenter.model.employee.Director;
import com.almundo.callcenter.model.employee.Employee;
import com.almundo.callcenter.model.employee.Operator;
import com.almundo.callcenter.model.employee.Supervisor;
import com.almundo.callcenter.objectpool.DirectorPool;
import com.almundo.callcenter.objectpool.OperatorPool;
import com.almundo.callcenter.objectpool.SupervisorPool;
import com.almundo.callcenter.queue.CallQueue;

/**
 * Process incoming calls using the Employeeds available
 * 
 * @author lsuarezl
 *
 */
public class Dispatcher implements Runnable {

	private static final Logger log = LogManager.getLogger(Dispatcher.class);

	private Integer callWait;
	

	public Dispatcher() {
		this.callWait = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.CALL_WAIT.conf()));
	}

	/**
	 * 
	 * @param callWait
	 *            time waiting for new incoming calls to process
	 */
	public Dispatcher(Integer callWait) {
		this.callWait = callWait;
	}
	
	
	public static void main(String[] args) throws Exception {
		Long startTime = System.currentTimeMillis();
		int threadNum = Integer.parseInt(Config.getInstance().getProperty(ConfigValues.THREADS_NUM.conf()));
		log.info("Start at " + startTime);

		ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
		
		CallQueue.getInstance();
		OperatorPool.getInstance();
		SupervisorPool.getInstance();
		DirectorPool.getInstance();
		//create dispatcher threads
		for (int i = 1; i < threadNum; i++) {
			Runnable dispatcher = new Dispatcher();
			executorService.execute(dispatcher);
		}
		Runnable caller = new Caller();
		executorService.submit(caller);
		executorService.shutdown();
		while (!executorService.isTerminated()) {

		}
		log.info("Calls processed:");
		CallQueue.getInstance().getAnsweredCalls().forEach(call -> log.info(call.toString()));
		Long finishTime = System.currentTimeMillis();
		log.info("Finished at " + finishTime + " Total time " + ((finishTime - startTime)));
	}
	

	/**
	 * Assigns call to employee
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void processCallQueues() throws InterruptedException {
		Call call = CallQueue.getInstance().getNextCall();
		if (call == null) {
			log.info("Waiting for incoming calls " + callWait + " milliseconds");
			TimeUnit.MILLISECONDS.sleep(callWait);
		}
		Employee employee = OperatorPool.getInstance().getResource();
		if (employee == null) {
			employee = SupervisorPool.getInstance().getResource();
		}
		if (employee == null) {
			employee = DirectorPool.getInstance().getResource();
		}
		if (employee != null) {
			dispatchCall(call, employee);
		}
	}

	/**
	 * Asigns a call to an employee
	 * @param call
	 * @param employee
	 * @throws InterruptedException
	 */
	public void dispatchCall(Call call, Employee employee) throws InterruptedException {
		try {
			if (call != null) {
				employee.answerCall(call);
				CallQueue.getInstance().addAnsweredCall(call);
			}
		} catch (CallCenterException cce) {
			log.catching(cce);
		} finally {
			releaseEmployee(employee);
		}
	}

	/**
	 * When the employee finish a call return into available employers pool
	 * @param employee
	 * @throws InterruptedException
	 */
	private void releaseEmployee(Employee employee) throws InterruptedException {
		if (employee instanceof Operator) {
			OperatorPool.getInstance().releaseResource((Operator) employee);
		}
		if (employee instanceof Supervisor) {
			SupervisorPool.getInstance().releaseResource((Supervisor) employee);
		}
		if (employee instanceof Director) {
			DirectorPool.getInstance().releaseResource((Director) employee);
		}

	}

	/**
	 * Stop processing calls
	 * 
	 * @return true when there are not more enqueued calls <br/> 
	 * waiting time is 0 or all calls were processed
	 */
	private boolean keepRuning() {
		Integer callsTotal = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.CALLS_TOTAL.conf()));
		Integer callsAnswered = CallQueue.getInstance().getAnsweredCalls().size();
		if(callsAnswered == callsTotal)
			return false;
		if (CallQueue.getInstance().getIncomingCallSize() > 0)
			return true;
		return (callWait > 0)?true:false;
	}

	@Override
	public void run() {
		while (keepRuning()) {
			try {
				processCallQueues();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				log.catching(e);
			}
		}
	}

}
