package com.bth.models;

import java.util.Date;

import com.bth.annotations.SQLColumn;
import com.bth.annotations.SQLEntity;
import com.bth.annotations.SQLKey;

@SQLEntity(tableName = "Device")
public class Device {
	
	@SQLColumn(name = "DeviceId")
	@SQLKey(generated = true)
	private Long deviceId;
	
	@SQLColumn(name = "AccountId")
	private Long accountId;
	
	@SQLColumn(name = "Platform")
	private int platform;
	
	@SQLColumn(name = "FirebaseKey")
	private String firebaseKey;
	
	@SQLColumn(name = "CreateDate")
	private Date createDate;
	
	@SQLColumn(name = "AmendDate")
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
