package com.ktdsuniversity.edu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.project.dao.UsersDao;
import com.ktdsuniversity.edu.project.vo.UsersVO;

@Service
public class UserserviceImpl implements Usersservice{
	@Autowired
	private UsersDao usersDao;

	@Override
	public boolean createNewUsers(UsersVO usersVO) {
		int emailCount = usersDao.getEmailCount(usersVO.getEmail());
		if(emailCount >0) {
			throw new IllegalArgumentException("email이 이미 사용중 입니다.");
		}
		int insertCount = usersDao.createNewUser(usersVO);
		return insertCount>0;
	}
	
}
