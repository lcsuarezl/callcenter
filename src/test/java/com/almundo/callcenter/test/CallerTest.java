package com.almundo.callcenter.test;

import com.almundo.callcenter.controller.Caller;
import com.almundo.callcenter.controller.Dispatcher;
import com.almundo.callcenter.objectpool.DirectorPool;
import com.almundo.callcenter.objectpool.OperatorPool;
import com.almundo.callcenter.objectpool.SupervisorPool;
import com.almundo.callcenter.queue.CallQueue;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;

public class CallerTest {

	/**
	 * Validate that Caller is able to make as many calls as you want with the
	 * duration you need
	 */
	@Test
	public void addCallTest() {
		CallQueue callQueue = CallQueue.getInstance();
		Caller caller = new Caller(10, 0, 5, 10);
		caller.run();
		Assert.assertEquals(callQueue.getIncomingCallSize(), 10);
	}

	/**
	 * Validate that Dispatcher can handle all enqueued calls
	 */
	@Test
	public void dispatcherTest() {
		CallQueue callQueue = CallQueue.getInstance();
		Caller caller = new Caller(10, 0, 1, 3);
		caller.run();
		Dispatcher dispatcher = new Dispatcher(0);
		dispatcher.run();
		Assert.assertEquals(callQueue.getAnsweredCalls().size(), 10);
	}

	/**
	 * Validate that the CallCenter can handle 10 calls in almost same time as
	 * the longest call duration
	 */
	@Test
	public void concurrenceTest() {
		Integer threads = 10;
		Long startTime = System.currentTimeMillis();
		Integer maxDuration = 3;
		CallQueue callQueue = CallQueue.getInstance();
		OperatorPool.getInstance();
		SupervisorPool.getInstance();
		DirectorPool.getInstance();
		Runnable caller = new Caller(10, 0, 1, maxDuration);
		caller.run();
		ExecutorService executorService = Executors.newFixedThreadPool(threads);
		for (int i = 1; i < threads; i++) {
			Runnable dispatcher = new Dispatcher(0);
			executorService.execute(dispatcher);
		}
		executorService.shutdown();
		// Wait until all threads are finish
		while (!executorService.isTerminated()) {

		}
		Long endTime = System.currentTimeMillis();
		Long time = (endTime - startTime) / 1000;
		// Answer all incoming calls
		Assert.assertEquals(callQueue.getAnsweredCalls().size(), 10);
		// The total time is less than the call maxDuration plus 1
		Assert.assertTrue(time < ((long) (maxDuration + 1)));

	}

}
