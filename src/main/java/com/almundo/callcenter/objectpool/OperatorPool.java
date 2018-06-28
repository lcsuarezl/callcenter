package com.almundo.callcenter.objectpool;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.model.employee.Operator;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.*;

public class OperatorPool extends ResourcePool<Operator> {
	
	private static OperatorPool instance;
	
	public static OperatorPool getInstance(){
		if(instance == null){
			instance = new OperatorPool();
		}
		return instance;
	}
	
	private OperatorPool() {
		locked = new LinkedBlockingQueue<Operator>();
		unlocked = new LinkedBlockingQueue<Operator>();
		loadPool();
	}
	
	protected void loadPool() {
		this.ammount = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.OPERATORS.conf()));
		IntStream.rangeClosed(1, ammount).forEach(i -> {
			try {
				this.unlocked.put(new Operator((long)(1000 + i)));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				log.catching(e);
			}
		});
	}

}
