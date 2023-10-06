package com.ktdsuniversity.edu.project.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.project.vo.UsersVO;
@Repository
public class UsersDaoImpl extends SqlSessionDaoSupport implements UsersDao{
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	@Override
	public int getEmailCount(String email) {
		return getSqlSession().selectOne("getEmailCount",email);
	}

	@Override
	public int createNewUser(UsersVO usersVO) {
		return getSqlSession().insert("createNewUser",usersVO);
	}

}
