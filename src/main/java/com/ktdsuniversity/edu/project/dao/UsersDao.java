package com.ktdsuniversity.edu.project.dao;

import com.ktdsuniversity.edu.project.vo.UsersVO;

public interface UsersDao {
	public int getEmailCount(String email);
	public int createNewUser(UsersVO usersVO);
}
