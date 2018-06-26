package com.almundo.callcenter.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.objectpool.DirectorPool;
import com.almundo.callcenter.objectpool.OperatorPool;
import com.almundo.callcenter.objectpool.SupervisorPool;
import com.almundo.callcenter.queue.CallQueue;

/**
 * Simulates the call center with a thread generating incoming calls <br/>
 * and a pool of employeers answering. 
 * @author lsuarezl
 *
 */
public class CallCenter {

	private static Integer THREADS_NUM;

	private static final Logger log = LogManager.getLogger(CallCenter.class);

	public static void main(String args[]) throws Exception {
		Long startTime = System.currentTimeMillis();
		THREADS_NUM = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.THREADS_NUM.conf()));
		log.info("Start at " + startTime);

		ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUM);
		
		CallQueue.getInstance();
		OperatorPool.getInstance();
		SupervisorPool.getInstance();
		DirectorPool.getInstance();
		//create dispatcher threads
		for (int i = 1; i < THREADS_NUM; i++) {
			Runnable dispatcher = new Dispatcher();
			executorService.execute(dispatcher);
		}
		Runnable caller = new Caller();
		executorService.submit(caller);
		executorService.shutdown();
		while (!executorService.isTerminated()) {

		}
		log.info("Calls processed:");
		CallQueue.getInstance().getAnsweredCalls().forEach((call) -> log.info(call.toString()));
		Long finishTime = System.currentTimeMillis();
		log.info("Finished at " + finishTime + " Total time " + ((finishTime - startTime)));
	}
	
}
