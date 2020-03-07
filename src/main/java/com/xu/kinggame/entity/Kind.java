package com.xu.kinggame.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Kind {

	private Long kindId;
	private String kindTitle;
	private Byte isDelete;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date kindDate;
	public Long getKindId() {
		return kindId;
	}
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}
	public String getKindTitle() {
		return kindTitle;
	}
	public void setKindTitle(String kindTitle) {
		this.kindTitle = kindTitle;
	}
	public Byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
	public Date getKindDate() {
		return kindDate;
	}
	public void setKindDate(Date kindDate) {
		this.kindDate = kindDate;
	}
	@Override
	public String toString() {
		return "Kind [kindId=" + kindId + ", kindTitle=" + kindTitle + ", isDelete=" + isDelete + ", kindDate="
				+ kindDate + "]";
	}
	
	
	
	
}
