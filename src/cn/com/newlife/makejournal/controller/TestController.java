package cn.com.newlife.makejournal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/test")
	private ModelAndView toTest() {
		System.out.println("I am in TestController-->test");
		return new ModelAndView("test");
	}

}
