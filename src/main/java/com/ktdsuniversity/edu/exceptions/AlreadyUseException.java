package com.ktdsuniversity.edu.exceptions;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public class AlreadyUseException extends RuntimeException{

	/**
	 * 회원가입할 떄 이미 가입된 이메일로 가입을 시도하면 발생하는 예외
	 * 화면으로 가입 정보를 전달해 줘여함 
	 */
	private static final long serialVersionUID = 6062148073962465674L;
	/**
	 * 가입 정보
	 */
	private MemberVO memberVO;
	public AlreadyUseException(MemberVO memberVO, String message) {
		super(message);
		this.memberVO = memberVO;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
}
