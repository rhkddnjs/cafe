package com.ktdsuniversity.edu.bbs.web;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.bbs.service.BoardService;
import com.ktdsuniversity.edu.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.beans.FileHandler;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BoardController {
	
	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list")
	public ModelAndView viewBoardList() {
		BoardListVO boardListVO = boardService.getAllBoard();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardlist");
		modelAndView.addObject("boardList", boardListVO);
		
		return modelAndView;
	}
	
	@GetMapping("/board/write")
	public String viewBoardWritePage() {
		return "board/boardwrite";
	}
	
	@PostMapping("/board/write")
	public ModelAndView doBoardWrite (@Valid @ModelAttribute BoardVO boardVO
									// Validation 실패 결과를 갖고있다.
									// @Valid 바로 다음에 있어야 한다. (순서 매우 중요!)
									, BindingResult bindingResult 
			                        , @RequestParam MultipartFile file
			                        , HttpServletRequest request
									, @SessionAttribute("_LOGIN_USER_") MemberVO memberVO){
		
		// 요청자의 IP 정보를 ipAddr 변수에 할당한다.
		boardVO.setIpAddr(request.getRemoteAddr());
		
		System.out.println("제목: " + boardVO.getSubject());
		System.out.println("이메일: " + boardVO.getEmail());
		System.out.println("내용: " + boardVO.getContent());
		System.out.println("등록일: " + boardVO.getCrtDt());
		System.out.println("수정일: " + boardVO.getMdfyDt());
		System.out.println("FileName: " + boardVO.getFileName());
		System.out.println("OriginFileName: " + boardVO.getOriginFileName());
		
		ModelAndView modelAndView = new ModelAndView();
		
		// Validation 체크한 것중 실패한 것이 있다면,
		if (bindingResult.hasErrors()) {
			// 화면을 보여준다.
			// 게시글 등록은 하지 않는다.
			modelAndView.setViewName("board/boardwrite");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;			
		}
		
		boardVO.setEmail(memberVO.getEmail());
		// 게시글 등록
		boolean isSuccess = boardService.createNewBoard(boardVO, file);
		if (isSuccess) {
			// 게시글 등록 결과가 성공이라면 
			// /board/list URL 로 이동한다.
			modelAndView.setViewName("redirect:/board/list");
			return modelAndView;
		}
		else {
			// 게시글 등록 결과가 실패라면
			// 게시글 등록(작성) 화면으로 데이터를 보내준다.
			// 게시글 등록(작성) 화면에서 boardVO 값으로 등록 값을 설정해야 한다.
			modelAndView.setViewName("board/boardwrite");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;
		}
	}
	
	@GetMapping("/board/view") // http://localhost:8080/board/view?id=1
	public ModelAndView viewOneBoard(@RequestParam int id) {
		BoardVO boardVO = boardService.getOneBoard(id, true);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardview");
		modelAndView.addObject("boardVO", boardVO);
		return modelAndView;
	}
	
	@GetMapping("/board/modify/{id}")
	public ModelAndView viewBoardModifyPage (@PathVariable int id
										   , @SessionAttribute("_LOGIN_USER_") MemberVO memberVO) {
		// 게시글 수정을 위해 게시글의 내용을 조회한다.
		// 게시글 조회와 동일한 코드 호출
		BoardVO boardVO = boardService.getOneBoard(id, false);
		if (!boardVO.getEmail().equals(memberVO.getEmail())) {
			throw new IllegalArgumentException("잘못된 접근입니다!");
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardmodify");
		modelAndView.addObject("boardVO", boardVO);
		return modelAndView;
	}
	
	@GetMapping("/board/file/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable int id) {
		
		// 파일 정보를 얻어오기 위해 게시글 정보를 조회한다.
		BoardVO boardVO = boardService.getOneBoard(id, false);
		if (boardVO == null) {
			throw new IllegalArgumentException("잘못된 접근입니다.");
		}
		
		// 서버에 등록되어 있는 파일을 가져온다.
		File storedFile = fileHandler.getStoredFile(boardVO.getFileName());
		
		// 다운로드 한다.
		return fileHandler.getResponseEntity(storedFile,
											 boardVO.getOriginFileName());
	}	
	
	@PostMapping("/board/modify")
	public ModelAndView doBoardUpdate(@Valid @ModelAttribute BoardVO boardVO,
									  BindingResult bindingResult,
									  Model model,
									  @RequestParam MultipartFile file,
									  @SessionAttribute("_LOGIN_USER_") MemberVO memberVO) {
		System.out.println("ID: " + boardVO.getId());
		System.out.println("제목: " + boardVO.getSubject());
		System.out.println("이메일: " + boardVO.getEmail());
		System.out.println("내용: " + boardVO.getContent());
		System.out.println("등록일: " + boardVO.getCrtDt());
		System.out.println("수정일: " + boardVO.getMdfyDt());
		System.out.println("FileName: " + boardVO.getFileName());
		System.out.println("OriginFileName: " + boardVO.getOriginFileName());
		
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("board/boardmodify");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;
		}
		
		BoardVO originBoardVO = boardService.getOneBoard(boardVO.getId(), false);
		if (!originBoardVO.getEmail().equals(memberVO.getEmail())) {
			throw new IllegalArgumentException("잘못된 접근입니다!");
		}

		// 게시글 수정
		boolean isSuccess = boardService.updateOneBoard(boardVO, file);
		
		if (isSuccess) {
			// 게시글 수정 결과가 성공이라면
			// /board/view?id=id URL 로 이동한다.
			modelAndView.setViewName("redirect:/board/view?id=" + boardVO.getId());
			return modelAndView;
		}
		else {
			// 게시글 수정 결과가 실패라면
			// 게시글 수정 화면으로 데이터를 보내준다.
			modelAndView.setViewName("board/boardmodify");
			modelAndView.addObject("boardVO",boardVO);
			return modelAndView;
		}
	}
	
	@GetMapping("/board/delete/{id}")
	public String doDeleteBoard(@PathVariable int id
							  , @SessionAttribute("_LOGIN_USER_") MemberVO memberVO) {
		
		BoardVO originBoardVO = boardService.getOneBoard(id, false);
		if (!originBoardVO.getEmail().equals(memberVO.getEmail())) {
			throw new IllegalArgumentException("잘못된 접근입니다!");
		}

		boolean isSuccess = boardService.deleteOneBoard(id);
		if (isSuccess) {
			return "redirect:/board/list";
		}
		else {
			return "redirect:/board/view?id=" + id;
		}
	}
}
