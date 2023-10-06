package com.ktdsuniversity.edu.bbs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.bbs.service.ReplyService;
import com.ktdsuniversity.edu.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Controller
public class ReplyController {
@Autowired
private ReplyService replyService;

@GetMapping("/board/reply/{boardId}")
public Map<String, Object> getAllReplies(@PathVariable int boardId){
	List<ReplyVO> replyList= replyService.getAllReplies(boardId);
	
	Map<String, Object> resultMap = new HashMap<>();
	resultMap.put("count", replyList.size());
	resultMap.put("replies", replyList);
	return resultMap;
}

}

