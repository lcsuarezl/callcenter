package com.almundo.callcenter.queue;

import java.util.concurrent.LinkedBlockingQueue;

import com.almundo.callcenter.model.call.Call;

public class CallQueue {
	
	private static CallQueue instance;
	
	private LinkedBlockingQueue<Call> calls;
	
	
	public static CallQueue getInstance(){
		if(instance == null){
			instance = new CallQueue();
		}
		return instance;
	}
	
	private CallQueue(){
		this.calls = new LinkedBlockingQueue<Call>();
	}
	
	public void addCall(Call call) throws InterruptedException{
		this.calls.put(call);
	}
	
	public Call getCall(){
		return this.calls.poll();
	}

	public int getSize() {
		return calls.size();
	}

}
