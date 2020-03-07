package com.xu.kinggame.controller.cus;

import java.io.Serializable;

public class CustomerBlogList implements Serializable {

	private Long blogId;
	private String blogTitle;
	public Long getBlogId() {
		return blogId;
	}
	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	@Override
	public String toString() {
		return "CustomerBlogList [blogId=" + blogId + ", blogTitle=" + blogTitle + "]";
	}
	
	
	
}
