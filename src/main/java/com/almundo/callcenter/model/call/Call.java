package com.almundo.callcenter.model.call;

public class Call {
	
	private Long createdTime;
	private Long answeredTime;
	private Long finisedTime;
	private Integer acd;
	private String answeredBy;

	public Call(Long createdTime, Integer acd) {
		super();
		this.createdTime = createdTime;
		this.acd = acd;
	}

	public Call() {
		super();
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

	public String getAnsweredBy() {
		return answeredBy;
	}

	public void setAnsweredBy(String answeredBy) {
		this.answeredBy = answeredBy;
	}

	public Integer getAcd() {
		return acd;
	}

	public void setAcd(Integer acd) {
		this.acd = acd;
	}

	@Override
	public String toString() {
		return "Call [createdTime=" + createdTime + ", answeredTime=" + answeredTime + ", finisedTime=" + finisedTime
				+ ", acd=" + acd + ", answeredBy=" + answeredBy + "]";
	}
	
	
	

}
