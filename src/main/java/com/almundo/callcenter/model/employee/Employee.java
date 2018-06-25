package com.almundo.callcenter.model.employee;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.almundo.callcenter.config.EmployeeStatus;
import com.almundo.callcenter.model.call.Call;

public abstract class Employee {

	private Long id;
	private String name;
	private EmployeeStatus status;
	
	private static final Logger log = LogManager.getLogger(Employee.class);
	
	public Employee(Long id){
		this.id = id;
		this.status = EmployeeStatus.FREE;
	}
	
	public Employee(String name, Long id){
		this.name = name;
		this.id = id;
		this.status = EmployeeStatus.FREE;
	}
	
	public abstract void answerCall(Call call);

	protected void answerCallDefault(Call call, String msg)  {
		log.info(msg);
		try {
			this.status = EmployeeStatus.BUSSY;
			call.setAnsweredBy(name);
			call.setAnsweredTime(System.currentTimeMillis());
	        TimeUnit.SECONDS.sleep(call.getAcd());
	        call.setFinisedTime(System.currentTimeMillis());
	    }
	    catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		this.status = EmployeeStatus.FREE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	

}
