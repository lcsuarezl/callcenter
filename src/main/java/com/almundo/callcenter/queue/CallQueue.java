package com.almundo.callcenter.queue;

import java.util.concurrent.LinkedBlockingQueue;

import com.almundo.callcenter.model.call.Call;

public class CallQueue {
	
	private static CallQueue instance;
	
	private LinkedBlockingQueue<Call> incomingCalls;
	private LinkedBlockingQueue<Call> answeredCalls;
	
	
	public static CallQueue getInstance(){
		if(instance == null){
			instance = new CallQueue();
		}
		return instance;
	}
	
	private CallQueue(){
		this.incomingCalls = new LinkedBlockingQueue<Call>();
		this.answeredCalls = new LinkedBlockingQueue<Call>();
	}
	
	public void addIncomingCall(Call call) throws InterruptedException{
		this.incomingCalls.put(call);
	}
	
	
	public void addAnsweredCall(Call call) throws InterruptedException{
		this.answeredCalls.put(call);
	}
	
	public boolean isDone(){
		if(this.incomingCalls.size() > 0)
			return false; 
		return true;
	}
	
	public int getIncomingCallSize(){
		return this.incomingCalls.size();
	}
	
	public Call getNextCall(){
		if(this.incomingCalls.size()>0)
			return this.incomingCalls.poll();
		return null;
	}

	public LinkedBlockingQueue<Call> getAnsweredCalls() {
		return answeredCalls;
	}
	
}
