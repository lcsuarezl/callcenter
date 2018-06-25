package com.almundo.callcenter.objectpool;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.model.employee.Operator;

import java.util.stream.*;

public class OperatorPool extends EmployeePool {
	
	public static OperatorPool instance;
	
	public static OperatorPool getInstance(){
		if(instance == null){
			instance = new OperatorPool();
		}
		return instance;
	}
	
	private OperatorPool() {
		loadPool();
	}
	
	@Override
	protected void loadPool() {
		this.ammount = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.OPERATORS.conf()));
		IntStream.rangeClosed(1, ammount).forEach(i -> this.unlocked.put(new Operator((long)i), (long)i));
	}

}
