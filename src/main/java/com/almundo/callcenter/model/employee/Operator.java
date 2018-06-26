package com.almundo.callcenter.model.employee;

import com.almundo.callcenter.exception.CallCenterException;
import com.almundo.callcenter.model.call.Call;

public class Operator extends Employee {

	public Operator(Long id) {
		super(id);
	}

	@Override
	public void answerCall(Call call) throws CallCenterException {
		String msg = null;
		if (call != null)
			msg = "Operator id[" + getId() + "] is answering the call id[" + call.getId() + "] duration " + call.getAcd()
					+ " seconds";
		super.answerCallDefault(call, msg);
	}

}
