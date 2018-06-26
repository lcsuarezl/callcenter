package com.almundo.callcenter.config;

public enum ConfigValues {
	
	OPERATORS("com.almundo.callcenter.config.operators"),
	SUPERVISORS("com.almundo.callcenter.config.supervisors"),
	DIRECTORS("com.almundo.callcenter.config.directors"),
	DURATION_MIN("com.almundo.callcenter.config.duration.min"),
	DURATION_MAX("com.almundo.callcenter.config.duration.max"),
	THREADS_NUM("com.almundo.callcenter.config.threads.num"),
	CALL_DELAY("com.almundo.callcenter.config.calls.delay"),
	CALL_WAIT("com.almundo.callcenter.config.calls.wait.time"),
	CALLS_TOTAL("com.almundo.callcenter.config.calls.total");
	
	private String conf;
	
	ConfigValues(String conf){
		this.conf=conf;
	}
	
	public String conf(){
		return conf;
	}

}
