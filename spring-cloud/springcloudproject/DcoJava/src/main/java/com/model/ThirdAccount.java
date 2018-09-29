package com.model;

public class ThirdAccount {
	private int tid;
	private String thirdUserId;
	private String thirdAccount;
	private int thirdType;
	private String thirdToken;
	private String ctTime;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getThirdUserId() {
		return thirdUserId;
	}
	public void setThirdUserId(String thirdUserId) {
		this.thirdUserId = thirdUserId;
	}
	public String getThirdAccount() {
		return thirdAccount;
	}
	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}
	public int getThirdType() {
		return thirdType;
	}
	public void setThirdType(int thirdType) {
		this.thirdType=thirdType;
	}
	public String getThirdToken() {
		return thirdToken;
	}
	public void setThirdToken(String thirdToken) {
		this.thirdToken = thirdToken;
	}
	public String getCtTime() {
		return ctTime;
	}
	public void setCtTime(String ctTime) {
		this.ctTime=ctTime;
	}
	@Override
	public String toString() {
		return "ThirdAccount [tid=" + tid + ", thirdUserId=" + thirdUserId + ", thirdAccount=" + thirdAccount
				+ ", thirdType=" + thirdType + ", thirdToken=" + thirdToken + ", ctTime=" + ctTime + "]";
	}
}
