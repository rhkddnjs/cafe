package com.ktdsuniversity.edu.exceptions;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public class UserIdendifyNotMatchException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3234883133196074207L;
	
	private MemberVO memberVO;
	public UserIdendifyNotMatchException(MemberVO memberVO,String message) {
		super(message);
		this.memberVO=memberVO;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
}
