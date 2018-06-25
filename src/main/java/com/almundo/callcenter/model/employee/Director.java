package com.almundo.callcenter.model.employee;

import com.almundo.callcenter.model.call.Call;

public class Director extends Employee {
	
	public Director(Long id) {
		super(id);
	}
	
	public Director(String name, Long id) {
		super(name, id);
	}

	@Override
	public void answerCall(Call call) {
		String msg = "Call answered by Director "+getName();
		super.answerCallDefault(call, msg);
	}

}
