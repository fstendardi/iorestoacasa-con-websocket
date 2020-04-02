package com.stenda.websocketdemo.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.stenda.websocketdemo.service.CustomPrincipal;

@Controller
public class MVCController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/admin")
	public ModelAndView admin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		CustomPrincipal principal = ((CustomPrincipal) auth.getPrincipal());
		
		Map<String, Object> model = new HashMap<>();
		model.put("user", principal.getUser());
		
		return new ModelAndView("admin", model);

	}
}
