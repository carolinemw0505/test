package com.dco.eureka.client.model;

public class ThirdState {
	private int id;
	private String state;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "ThirdState [id=" + id + ", state=" + state + "]";
	}
}
