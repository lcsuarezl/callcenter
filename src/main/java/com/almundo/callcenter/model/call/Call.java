package com.almundo.callcenter.model.call;

public class Call {
	
	private Long id;
	private Long createdTime;
	private Long answeredTime;
	private Long finisedTime;
	private Integer acd;
	private String message;

	public Call(Long id, Integer acd) {
		super();
		this.id=id;
		this.createdTime = System.currentTimeMillis();
		this.acd = acd;
	}
	

	public Call() {
		super();
		this.createdTime = System.currentTimeMillis();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getAnsweredTime() {
		return answeredTime;
	}

	public void setAnsweredTime(Long answeredTime) {
		this.answeredTime = answeredTime;
	}


	public Long getFinisedTime() {
		return finisedTime;
	}

	public void setFinisedTime(Long finisedTime) {
		this.finisedTime = finisedTime;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getAcd() {
		return acd;
	}

	public void setAcd(Integer acd) {
		this.acd = acd;
	}


	@Override
	public String toString() {
		return "Call [id=" + id + ", createdTime=" + createdTime + ", answeredTime=" + answeredTime + ", finisedTime="
				+ finisedTime + ", acd=" + acd + ", message=" + message + "]";
	}


}
