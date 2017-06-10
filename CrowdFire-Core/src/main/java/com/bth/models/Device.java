package com.bth.models;

import java.util.Date;

public class Device {
	
	private Long deviceId;
	private Long accountId;
	private int platform;
	private String firebaseKey;
	private Date createDate;
	private Date amendDate;
	
	public Long getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public int getPlatform() {
		return platform;
	}
	
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	
	public String getFirebaseKey() {
		return firebaseKey;
	}
	
	public void setFirebaseKey(String firebaseKey) {
		this.firebaseKey = firebaseKey;
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
