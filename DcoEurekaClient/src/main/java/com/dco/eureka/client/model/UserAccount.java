package com.dco.eureka.client.model;

public class UserAccount {
	private int userId;
	private String account;
	private String password;
	private String email;
	private String phone;
	private int status;
	private String ctTime;
	private String mdTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCtTime() {
		return ctTime;
	}
	public void setCtTime(String ctTime) {
		this.ctTime = ctTime;
	}
	public String getMdTime() {
		return mdTime;
	}
	public void setMdTime(String mdTime) {
		this.mdTime = mdTime;
	}
	
	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", account=" + account + ", password=" + password + ", email=" + email
				+ ", phone=" + phone + ", status=" + status + ", ctTime=" + ctTime + ", mdTime=" + mdTime + "]";
	}

}