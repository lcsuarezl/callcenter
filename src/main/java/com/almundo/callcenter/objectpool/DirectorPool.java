package com.almundo.callcenter.objectpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.model.employee.Director;

public class DirectorPool extends ResourcePool<Director> {
 
	public static DirectorPool instance;

	public static DirectorPool getInstance() {
		if (instance == null) {
			instance = new DirectorPool();
		}
		return instance;
	}

	private DirectorPool() {
		locked = new LinkedBlockingQueue<Director>();
		unlocked = new LinkedBlockingQueue<Director>();
		loadPool();
	}

	protected void loadPool() {
		this.ammount = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.DIRECTORS.conf()));
		IntStream.rangeClosed(1, ammount).forEach(i -> {
			try {
				this.unlocked.put(new Director((long) i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
