package com.abcwebportal.webportal.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.abcwebportal.webportal.security.responsehandler.invlaidresponsehandler.InvalidLoginResponse;
import com.google.gson.Gson;

/**
 * Entry point for the validation implements
 * <code> AuthenticationEntryPoint </code>
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private InvalidLoginResponse invalidLoginResponse;
	@Autowired
	private Gson gson;

	/**
	 * Implementations should modify the headers on the <code>ServletResponse</code>
	 * as necessary to commence the authentication process.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authExe)
			throws IOException, ServletException {
		String jsonString = gson.toJson(invalidLoginResponse);
		response.setContentType("application/json");
		response.setStatus(401);
		response.getWriter().write(jsonString);
	}
}
