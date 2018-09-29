package com.model;

public class ThirdBasicInfo {
	private int bsid;
	private int tid;
	private String name;
	private String homePage;
	private int pubCount;
	private int fanCount;
	private int watchCount;
	private int starCount;
	private int readCount;
	private int commentCount;
	private int replyCount;
	private int sharedCount;
	private String regTime;
	public int getBsid() {
		return bsid;
	}
	public void setBsid(int bsid) {
		this.bsid = bsid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public int getPubCount() {
		return pubCount;
	}
	public void setPubCount(int pubCount) {
		this.pubCount = pubCount;
	}
	public int getFanCount() {
		return fanCount;
	}
	public void setFanCount(int fanCount) {
		this.fanCount = fanCount;
	}
	public int getWatchCount() {
		return watchCount;
	}
	public void setWatchCount(int watchCount) {
		this.watchCount = watchCount;
	}
	public int getStarCount() {
		return starCount;
	}
	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getSharedCount() {
		return sharedCount;
	}
	public void setSharedCount(int sharedCount) {
		this.sharedCount = sharedCount;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String date) {
		this.regTime=date;
	}
	@Override
	public String toString() {
		return "ThirdBasicInfo [bsid=" + bsid + ", tid=" + tid + ", name=" + name + ", homePage=" + homePage
				+ ", pubCount=" + pubCount + ", fanCount=" + fanCount + ", watchCount=" + watchCount + ", starCount="
				+ starCount + ", readCount=" + readCount + ", commentCount=" + commentCount + ", replyCount="
				+ replyCount + ", sharedCount=" + sharedCount + ", regTime=" + regTime + "]";
	}
}
