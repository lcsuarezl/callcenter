package com.almundo.callcenter.objectpool;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public abstract class ResourcePool<T> {

	
	protected LinkedBlockingQueue<T> locked;
	protected LinkedBlockingQueue<T> unlocked;
	
	protected Integer ammount;
	
	protected static final Logger log = LogManager.getLogger(ResourcePool.class);
	
	protected abstract void loadPool() ;

	public synchronized T getResource() throws InterruptedException {
		T t = null;
		if (unlocked.isEmpty()) {
			t = unlocked.poll();
			locked.put(t);
		}
		return t;
	}

	public synchronized  void releaseResource(T t) throws InterruptedException {
		locked.remove(t);
		put(unlocked, t);
	}

	private void put(LinkedBlockingQueue<T> queue, T t) throws InterruptedException {
		queue.put(t);
	}

}
