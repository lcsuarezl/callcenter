package com.almundo.callcenter.objectpool;

import java.util.concurrent.LinkedBlockingQueue;


public abstract class ResourcePool<T> {

	
	protected LinkedBlockingQueue<T> locked, unlocked;
	
	protected Integer ammount;
	
	protected abstract void loadPool() ;

	public synchronized T getResource() throws InterruptedException {
		T t = null;
		if (unlocked.size() > 0) {
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
