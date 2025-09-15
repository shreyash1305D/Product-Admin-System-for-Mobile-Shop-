package com.adminsystem.MobileShop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

}
