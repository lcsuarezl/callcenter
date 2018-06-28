package com.almundo.callcenter.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.almundo.callcenter.config.Config;
import com.almundo.callcenter.config.ConfigValues;
import com.almundo.callcenter.model.call.Call;
import com.almundo.callcenter.queue.CallQueue;

/**
 * Manage the call creation
 * 
 * @author leon
 *
 */
public class Caller implements Runnable {

	private Integer totalCalls;
	private Integer callDelay;
	private Integer durationMin;
	private Integer durationMax;
	private static final Logger log = LogManager.getLogger(Caller.class);

	public Caller() {
		super();
		this.totalCalls = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.CALLS_TOTAL.conf()));
		this.callDelay = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.CALL_DELAY.conf()));
		this.durationMin = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.DURATION_MIN.conf()));
		this.durationMax = Integer.valueOf(Config.getInstance().getProperty(ConfigValues.DURATION_MAX.conf()));
	}

	/**
	 * @param totalCalls
	 *            that will be enqueued
	 * @param callDelay
	 *            time betwen call creation
	 * @param durationMin
	 *            min call duration
	 * @param durationMax
	 *            max call duration
	 */
	public Caller(Integer totalCalls, Integer callDelay, Integer durationMin, Integer durationMax) {
		super();
		this.totalCalls = totalCalls;
		this.callDelay = callDelay;
		this.durationMin = durationMin;
		this.durationMax = durationMax;
	}

	private void makeCall(Long id) throws InterruptedException {
		CallQueue.getInstance().addIncomingCall(createCall(id));
	}

	private Call createCall(Long id) {
		Call call = new Call(id, generateACD());
		log.info(" created callId["+call.getId()+"]");
		return call;
	}

	/**
	 * Generate the Average Call Duration time
	 * 
	 * @return
	 */
	private Integer generateACD() {
		Random r = new Random();
		return  r.nextInt(durationMax - durationMin) + durationMin;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < totalCalls; i++) {
				TimeUnit.MILLISECONDS.sleep(callDelay);
				makeCall((long) (i + 1));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.catching(e);
		}

	}

}
