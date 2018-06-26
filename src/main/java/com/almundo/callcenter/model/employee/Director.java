package com.almundo.callcenter.model.employee;

import com.almundo.callcenter.exception.CallCenterException;
import com.almundo.callcenter.model.call.Call;

public class Director extends Employee {

	public Director(Long id) {
		super(id);
	}

	@Override
	public void answerCall(Call call) throws CallCenterException {
		String msg = null;
		if (call != null)
			msg = "Director id[" + getId() + "] is answering  call id[" + call.getId() + "] time " + call.getAcd()
					+ " seconds";
		super.answerCallDefault(call, msg);
	}

}
