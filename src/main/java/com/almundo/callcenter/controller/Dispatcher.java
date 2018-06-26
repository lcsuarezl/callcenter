package com.almundo.callcenter.controller;

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

	/**
	 * Assigns call to employee
	 * 
	 * @throws InterruptedException
	 */
	public void processCallQueues() throws InterruptedException {
		Call call = CallQueue.getInstance().getNextCall();
		if (call == null) {
			log.info("Waiting for incoming calls " + callWait + " seconds");
			TimeUnit.SECONDS.sleep(callWait);
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
	 * @return true when there are not more enqueued calls and waiting time is 0
	 */
	private boolean keepRuning() {
		if (CallQueue.getInstance().getIncomingCallSize() > 0)
			return true;
		if (callWait > 0)
			return true;
		return false;
	}

	@Override
	public void run() {
		while (keepRuning()) {
			try {
				processCallQueues();
			} catch (InterruptedException e) {
				log.catching(e);
			}
		}
	}

}
