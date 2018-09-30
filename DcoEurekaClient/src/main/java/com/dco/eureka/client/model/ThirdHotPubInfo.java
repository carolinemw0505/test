package com.dco.eureka.client.model;

public class ThirdHotPubInfo {
	private int hrid;
	private int tid;
	private String title;
	private String content;
	private String linkAddr;
	private int commentCount;
	private int watchCount;
	private int starCount;
	private int forkCount;
	private int sharedCount;
	private String pubTime;
	public int getHrid() {
		return hrid;
	}
	public void setHrid(int hrid) {
		this.hrid = hrid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLinkAddr() {
		return linkAddr;
	}
	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
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
	public int getForkCount() {
		return forkCount;
	}
	public void setForkCount(int forkCount) {
		this.forkCount = forkCount;
	}
	public int getSharedCount() {
		return sharedCount;
	}
	public void setSharedCount(int sharedCount) {
		this.sharedCount = sharedCount;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String date) {
		this.pubTime=date;
	}
	@Override
	public String toString() {
		return "ThirdHotPubInfo [hrid=" + hrid + ", tid=" + tid + ", title=" + title + ", content=" + content
				+ ", linkAddr=" + linkAddr + ", commentCount=" + commentCount + ", watchCount=" + watchCount
				+ ", starCount=" + starCount + ", forkCount=" + forkCount + ", sharedCount=" + sharedCount
				+ ", pubTime=" + pubTime + "]";
	}
}
