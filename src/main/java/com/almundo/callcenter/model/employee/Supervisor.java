package com.almundo.callcenter.model.employee;

import com.almundo.callcenter.model.call.Call;

public class Supervisor extends Employee {

	public Supervisor(Long id) {
		super(id);
	}
	
	public Supervisor(String name, Long id) {
		super(name, id);
	}

	@Override
	public void answerCall(Call call) {
		String msg = "Call answered by Supervisor "+getName();
		super.answerCallDefault(call, msg);
		
	}

}
