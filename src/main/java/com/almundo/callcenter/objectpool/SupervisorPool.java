package com.almundo.callcenter.objectpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.model.employee.Supervisor;

public class SupervisorPool extends ResourcePool<Supervisor> {

	public static SupervisorPool instance;

	public static SupervisorPool getInstance() {
		if (instance == null) {
			instance = new SupervisorPool();
		}
		return instance;
	}

	private SupervisorPool() {
		locked = new LinkedBlockingQueue<Supervisor>();
		unlocked = new LinkedBlockingQueue<Supervisor>();
		loadPool();
	}

	protected void loadPool() {
		this.ammount = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.SUPERVISORS.conf()));
		IntStream.rangeClosed(1, ammount).forEach(i -> {
			try {
				this.unlocked.put(new Supervisor((long) (100+i)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
