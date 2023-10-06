package com.ktdsuniversity.edu.bbs.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ktdsuniversity.edu.bbs.service.BoardServiceImpl;
import com.ktdsuniversity.edu.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.beans.FileHandler;
import com.ktdsuniversity.edu.exceptions.MakeXlsxFileException;
import com.ktdsuniversity.edu.exceptions.PageNotFoundException;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class BoardController {
	private Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
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
		
		logger.debug("제목: " + boardVO.getSubject());
		logger.debug("이메일: " + boardVO.getEmail());
		logger.debug("내용: " + boardVO.getContent());
		logger.debug("등록일: " + boardVO.getCrtDt());
		logger.debug("수정일: " + boardVO.getMdfyDt());
		logger.debug("FileName: " + boardVO.getFileName());
		logger.debug("OriginFileName: " + boardVO.getOriginFileName());
		
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
			throw new PageNotFoundException("잘못된 접근입니다!");
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
			throw new PageNotFoundException("잘못된 접근입니다.");
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
		logger.debug("ID: " + boardVO.getId());
		logger.debug("제목: " + boardVO.getSubject());
		logger.debug("이메일: " + boardVO.getEmail());
		logger.debug("내용: " + boardVO.getContent());
		logger.debug("등록일: " + boardVO.getCrtDt());
		logger.debug("수정일: " + boardVO.getMdfyDt());
		logger.debug("FileName: " + boardVO.getFileName());
		logger.debug("OriginFileName: " + boardVO.getOriginFileName());
		
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("board/boardmodify");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;
		}
		
		BoardVO originBoardVO = boardService.getOneBoard(boardVO.getId(), false);
		if (!originBoardVO.getEmail().equals(memberVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다!");
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
			throw new PageNotFoundException("잘못된 접근입니다!");
		}

		boolean isSuccess = boardService.deleteOneBoard(id);
		if (isSuccess) {
			return "redirect:/board/list";
		}
		else {
			return "redirect:/board/view?id=" + id;
		}
	}
	@GetMapping("/board/excel/download")
	public ResponseEntity<Resource> downloadExelFile(){
		//엑셀로 만들 모든 게시글 조회
		BoardListVO boardListVO = boardService.getAllBoard();
		// xlsx 파일을 만든다.
		Workbook workbook = new SXSSFWorkbook(-1);
		// 엑셀 시트를 만든다.
		Sheet sheet = workbook.createSheet("게시글 목록");
		// 엑셀 시트에 행(row)를 만든다.
		Row row= sheet.createRow(0);
		// 행(row)에 열(cloumn)을 추가해 타이틀을 만든다.
		Cell cell = row.createCell(0);
		cell.setCellValue("번호");
		
		cell= row.createCell(1);
		cell.setCellValue("제목");
		
		cell= row.createCell(2);
		cell.setCellValue("첨부파일명");
		
		cell= row.createCell(3);
		cell.setCellValue("작성자 이메일");
		
		cell= row.createCell(4);
		cell.setCellValue("조회수");
		
		cell= row.createCell(5);
		cell.setCellValue("등록일");
		
		cell= row.createCell(6);
		cell.setCellValue("수정일");
		// 게시글의 수 만큼 행(row)을 만들고 열(column)을 만들어 데이터를 추가한다.
		List<BoardVO> boardList = boardListVO.getBoardList();
		//두번째 줄부터 데이터를 만든다.
		int rowIndex = 1;
		for(BoardVO boardVO : boardList) {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue(boardVO.getId()+"");
			
			cell = row.createCell(1);
			cell.setCellValue(boardVO.getSubject()+"");
			
			cell = row.createCell(2);
			cell.setCellValue(boardVO.getOriginFileName());
			
			cell = row.createCell(3);
			cell.setCellValue(boardVO.getEmail());
			
			cell = row.createCell(4);
			cell.setCellValue(boardVO.getViewCnt()+"");
			
			cell = row.createCell(5);
			cell.setCellValue(boardVO.getCrtDt());
			
			cell = row.createCell(6);
			cell.setCellValue(boardVO.getMdfyDt());
			rowIndex+=1;
		}
		// 작성한 문서를 파일로 만든다.
		File excelFile = fileHandler.getStoredFile("게시글_목록.xlsx");
		// OutputStream: Java에서 다른 시스템으로 데이터를 내보내는 것.
		OutputStream os = null;
		try {
			//파일 데이터를 다른 시스템으로 내보낸다.
			os = new FileOutputStream(excelFile);
			workbook.write(os);
		} catch (IOException e) {
			throw new MakeXlsxFileException("엑셀파일을 만들 수 없습니다.");
		}finally {
			//stream 데이터는 반드시 finally가 필요하다.
			//파일로 다 쓰고나면 엑셀 파일을 닫는다.
			try {
				workbook.close();} catch (IOException e) {}}
			//OutputStream을 쓰고 닫는다.
		if(os!=null) {
			//메모리에 저장하고 있는 OutputStream을 외부로 내보낸다.
			try {
				os.flush();} catch (IOException e) {}
			//OutputStream을 다 내보내고 나면 닫는다.
			try {os.close();} catch (IOException e) {}
		}
		// 엑셀 파일을 다운로드 한다.
		//파일명 생성
		//다운로드할 파일명이 한글일 때 , URLEncoder라는 것을 사용한다.
		String downloadFileName= URLEncoder.encode("게시글목록.xlsx",
									Charset.defaultCharset());
		//다운로드 시작
		return fileHandler.getResponseEntity(excelFile, downloadFileName);
	}
}
