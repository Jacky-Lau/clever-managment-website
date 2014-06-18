package edu.zju.bme.clever.website.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Just for setting the default target URL
	public LogoutSuccessHandlerImpl(String defaultTargetURL) {
		this.setDefaultTargetUrl(defaultTargetURL);
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		String httpSessionId = request.getSession().getId();
		String userName = ((UserDetails) authentication.getPrincipal())
				.getUsername();
		this.logger.trace("User {} with session {} authenticates.", userName,
				httpSessionId);
		
		super.onLogoutSuccess(request, response, authentication);
	}

}
