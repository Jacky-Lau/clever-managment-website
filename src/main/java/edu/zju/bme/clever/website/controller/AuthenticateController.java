package edu.zju.bme.clever.website.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticateController {

	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	@ResponseBody
	public boolean isAuthenticated(Authentication authentication) {
		return authentication != null;
	}
}
