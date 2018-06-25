package com.almundo.callcenter.model.employee;

import com.almundo.callcenter.model.call.Call;

public class Operator extends Employee {

	public Operator(Long id) {
		super(id);
	}
	
	public Operator(String name, Long id) {
		super(name, id);
	}

	@Override
	public void answerCall(Call call) {
		String msg = "Operator id "+getId()+ " is answering the call id ="+call.getCreatedTime();
		super.answerCallDefault(call, msg);
	}

}
