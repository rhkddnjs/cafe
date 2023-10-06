package com.ktdsuniversity.edu.project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.project.service.Usersservice;
import com.ktdsuniversity.edu.project.vo.UsersVO;
@Controller
public class UsersController {
	@Autowired
	private Usersservice usersservice;
	@GetMapping("/users/regist")
	public String viewRegistMemberPage() {
		return "users/userregist";
	}
	@PostMapping("/users/regist")
	public String doRegistUser(@ModelAttribute UsersVO usersVO
								,Model model) {
		boolean isSuccess = usersservice.createNewUsers(usersVO);
		if(isSuccess) {
			return("redirect:/users/login");
		}
		model.addAttribute("usersVO", usersVO);
		return "users/userregist";
	}
}
