package com.ktdsuniversity.edu.exceptions;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public class PageNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -487988232936340988L;
	public PageNotFoundException(String message) {
		super(message);
	}
}
