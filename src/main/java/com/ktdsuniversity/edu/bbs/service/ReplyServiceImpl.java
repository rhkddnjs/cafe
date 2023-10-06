package com.ktdsuniversity.edu.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.bbs.dao.ReplyDao;
import com.ktdsuniversity.edu.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.exceptions.PageNotFoundException;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDao replyDao;
	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return replyDao.getAllReplies(boardId);
	}

	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		int insertCount = replyDao.createNewReply(replyVO);
		return insertCount>0;
	}

	@Override
	public boolean deleteOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDao.getOneReply(replyId);
		if(!email.equals(replyVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return replyDao.deleteOneReply(replyId)>0;
	}

	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		ReplyVO orReplyVO = replyDao.getOneReply(replyVO.getReplyId());
		if(!replyVO.getEmail().equals(orReplyVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return replyDao.modifyOneReply(replyVO)>0;
	}

	@Override
	public boolean recommendOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDao.getOneReply(replyId);
		if(email.equals(replyVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return replyDao.recommendOneReply(replyId)>0;
	}

}
