package com.ktdsuniversity.edu.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloBootController {

//	 Spring 이 내부적으로 class 의 인스턴스를 만들어서 호출해줌
//		 @Controller 가 있어야 함! (3대장 중 하나)
//	public HelloBootController() {
//		System.out.println("생성자 호출되었음!");
//	}
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello Spring Boot Controller", HttpStatus.OK); 
	}
	
	@GetMapping("/hello2")
	public ResponseEntity<String> hello2() {
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("<head><title>Hello Boot!</title></head>");
		html.append("<body>");
		html.append("<div>하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이하이</div>");
		html.append("<div>Spring Boot에서 응답되었습니다!</div>");
		html.append("</body>");
		html.append("</html>");
		return new ResponseEntity<>(html.toString(), HttpStatus.OK);
	}
	
	@GetMapping("/hello3")
	// html 자체를 다 보여주고 싶을 때, String
	public String helloJsp() {
		return "helloboot";
	}
	
	@GetMapping("/hello4")
	// 내가 보여주고 싶은 template 과 template 에 전달하고 싶은 데이터를 함께 보여주고 싶을 때, ModelAndView
	// Controller 가 보내주는 값과 완전히 같아야 함
	public ModelAndView helloModelAndView() {
		ModelAndView view = new ModelAndView();
		view.setViewName("helloboot");
		view.addObject("myname", "Spring Boot~!");
		return view;
	}
	
	@GetMapping("/hello5")
	// 위 코드를 더 간결하게 사용 가능
	public String helloModel(Model model) {
		model.addAttribute("myname", "Cafe Demo~!");
		return "helloboot";
		
	}
}
