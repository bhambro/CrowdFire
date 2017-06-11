package com.bth.models;

import java.util.Date;

import com.bth.annotations.SQLColumn;
import com.bth.annotations.SQLEntity;
import com.bth.annotations.SQLKey;

@SQLEntity(tableName = "Account")
public class Account {
	
	@SQLColumn(name = "AccountId")
	@SQLKey(generated = false)
	private Long accountId;
	
	@SQLColumn(name = "EmailAddress")
	private String emailAddress;
	
	@SQLColumn(name = "State")
	private int state;
	
	@SQLColumn(name = "CreateDate")
	private Date createDate;
	
	@SQLColumn(name = "AmendDate")
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
