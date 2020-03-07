package com.xu.kinggame.entity;

public class User {

	private long adminId;
	
	private String loginname;
	private String loginpassword;
	private String adminnickname;
	private byte locked;
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpassword() {
		return loginpassword;
	}
	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}
	public String getAdminnickname() {
		return adminnickname;
	}
	public void setAdminnickname(String adminnickname) {
		this.adminnickname = adminnickname;
	}
	public byte getLocked() {
		return locked;
	}
	public void setLocked(byte locked) {
		this.locked = locked;
	}
	@Override
	public String toString() {
		return "User [adminId=" + adminId + ", loginname=" + loginname + ", loginpassword=" + loginpassword
				+ ", adminnickname=" + adminnickname + ", locked=" + locked + "]";
	}
	
}
