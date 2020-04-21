package com.douzone.jblog.vo;

public class CategoryVo {
	private int no;
	private String name;
	private String description;
	private String dateTime;
	private String id;
	private int numberOfPost;
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNumberOfPost() {
		return numberOfPost;
	}
	public void setNumberOfPost(int numberOfPost) {
		this.numberOfPost = numberOfPost;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", dateTime=" + dateTime
				+ ", id=" + id + ", numberOfPost=" + numberOfPost + ", title =" + title + "]";
	}


	
}
