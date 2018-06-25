package com.almundo.callcenter.test;

import com.almundo.callcenter.controller.Caller;
import com.almundo.callcenter.controller.Dispatcher;
import com.almundo.callcenter.queue.CallQueue;

import org.junit.Test;
import org.junit.Assert;

public class CallerTest {

	@Test
	public void addCallTest() {
		Caller caller = new Caller();
		caller.run();
		CallQueue callQueue = CallQueue.getInstance();
		Assert.assertEquals(callQueue.getSize(), 10);
	}

	@Test
	public void dispatcherTest() {
		Caller caller = new Caller();
		caller.run();
		Dispatcher dispatcher = new Dispatcher();
		dispatcher.run();
		Assert.assertEquals(dispatcher.getProcessedCalls().size(), 3);
	}
	
}
