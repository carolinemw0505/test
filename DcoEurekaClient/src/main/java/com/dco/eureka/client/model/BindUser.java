package com.dco.eureka.client.model;

public class BindUser {
	private String id;
	private String openId;
	private String avater;
	private String nickname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAvater() {
		return avater;
	}
	public void setAvater(String avater) {
		this.avater = avater;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "BindUser [id=" + id + ", openId=" + openId + ", avater=" + avater + ", nickname=" + nickname + "]";
	}
}
