package com.ktdsuniversity.edu.project.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsersVO {
	private String memId;
	@NotBlank(message = "이메일을 입력해주세요")
	@Email()
	private String email;
	private String loginPw;
	private String nicnm;
	private String infoGetYn;
	private String profPic;
	private String joinDate;
	private String memType;
	private String leaveYn;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginPw() {
		return loginPw;
	}
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}
	public String getNicnm() {
		return nicnm;
	}
	public void setNicnm(String nicnm) {
		this.nicnm = nicnm;
	}
	public String getInfoGetYn() {
		return infoGetYn;
	}
	public void setInfoGetYn(String infoGetYn) {
		this.infoGetYn = infoGetYn;
	}
	public String getProfPic() {
		return profPic;
	}
	public void setProfPic(String profPic) {
		this.profPic = profPic;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getMemType() {
		return memType;
	}
	public void setMemType(String memType) {
		this.memType = memType;
	}
	public String getLeaveYn() {
		return leaveYn;
	}
	public void setLeaveYn(String leaveYn) {
		this.leaveYn = leaveYn;
	}
	
}
