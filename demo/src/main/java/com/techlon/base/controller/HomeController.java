package com.techlon.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

	@RequestMapping(value = "/")
	public ModelAndView toIndex() {
		System.out.println("demo");
		ModelAndView mv = new ModelAndView("index");
		// TODO model
		return mv;
	}
}
