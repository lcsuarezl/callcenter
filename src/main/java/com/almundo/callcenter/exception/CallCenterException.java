package com.almundo.callcenter.exception;

public class CallCenterException extends Exception {

	private static final long serialVersionUID = 1L;

	public CallCenterException(){
		super();
	}
	
	public CallCenterException(String message){
		super(message);
	}
	
	public CallCenterException(String message, Throwable cause){
		super(message,cause);
	}
}
