package com.xu.kinggame.controller.cus;

import java.io.Serializable;

public class BlogList implements Serializable {

	private Long blogId;

    private String blogTitle;

    private String blogCoverImage;

    private Integer blogCategoryId;

    private String blogCategoryName;

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

	public String getBlogCoverImage() {
		return blogCoverImage;
	}

	public void setBlogCoverImage(String blogCoverImage) {
		this.blogCoverImage = blogCoverImage;
	}

	public Integer getBlogCategoryId() {
		return blogCategoryId;
	}

	public void setBlogCategoryId(Integer blogCategoryId) {
		this.blogCategoryId = blogCategoryId;
	}

	public String getBlogCategoryName() {
		return blogCategoryName;
	}

	public void setBlogCategoryName(String blogCategoryName) {
		this.blogCategoryName = blogCategoryName;
	}

	@Override
	public String toString() {
		return "BlogList [blogId=" + blogId + ", blogTitle=" + blogTitle + ", blogCoverImage=" + blogCoverImage
				+ ", blogCategoryId=" + blogCategoryId + ", blogCategoryName=" + blogCategoryName + "]";
	}
    
    
}
