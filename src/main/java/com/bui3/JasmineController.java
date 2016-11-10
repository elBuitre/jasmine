package com.bui3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class JasmineController {

	@RequestMapping(method=RequestMethod.GET)
	public String homePage() {
		return "homePage";
	}
	
	@RequestMapping("/admin")
	public String adminPage() {
		return "adminPage";
	}
}
