package com.bth.models;

import java.util.Date;

public class Account {
	
	private Long accountId;
	private String emailAddress;
	private int state;
	private Date createDate;
	private Date amendDate;
	
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getAmendDate() {
		return amendDate;
	}
	
	public void setAmendDate(Date amendDate) {
		this.amendDate = amendDate;
	}

}
