package com.almundo.callcenter.model.employee;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.almundo.callcenter.config.Status;
import com.almundo.callcenter.exception.CallCenterException;
import com.almundo.callcenter.model.call.Call;

public abstract class Employee {

	private Long id;
	private Status status;

	protected static final Logger log = LogManager.getLogger(Employee.class);

	public Employee(Long id) {
		this.id = id;
		this.status = Status.FREE;
	}

	public abstract void answerCall(Call call) throws CallCenterException, InterruptedException;

	protected void answerCallDefault(Call call, String message) throws CallCenterException, InterruptedException {
		log.info(message);
		if (this.status == Status.BUSSY)
			throw new CallCenterException("Employee id: " + this.id + " can't attend call: " + call.getId());
		if (call == null)
			throw new CallCenterException("Employee id: " + this.id + " can't attend null call");
		try {
			this.status = Status.BUSSY;
			call.setMessage(message);
			call.setAnsweredTime(System.currentTimeMillis());
			TimeUnit.SECONDS.sleep(call.getAcd());
			call.setFinisedTime(System.currentTimeMillis());
		} catch (InterruptedException e) {
			log.catching(e);
			throw e;
		}
		this.status = Status.FREE;
	}

	public Long getId() {
		return id;
	}

	public boolean isFree() {
		return (this.status == Status.FREE)?true:false;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", status=" + status + "]";
	}

}
