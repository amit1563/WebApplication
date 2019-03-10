package com.abcwebportal.webportal.security.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abcwebportal.webportal.model.user.User;
import com.abcwebportal.webportal.security.SecurityConstants;
import com.abcwebportal.webportal.serviceimpl.UserServiceImpl;

/**
 * <p>
 * Implements <code>OncePerRequestFilter</code> will validate the token and will
 * be called only once per request.
 *
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil tokenUtil;
	@Autowired
	private UserServiceImpl userServiceImpl;

	/**
	 * <P>
	 * Only abstract method of OncePerRequestFilter actual definition from super
	 * abstract implementation Same contract as for {@code doFilter}, but guaranteed
	 * to be just invoked once per request within a single request thread. See
	 * {@link #shouldNotFilterAsyncDispatch()} for details.
	 * <p>
	 * Provides HttpServletRequest and HttpServletResponse arguments instead of the
	 * default ServletRequest and ServletResponse ones.
	 * 
	 * basiclly security context holder will hold the authenticated user details
	 * after successfull execution of this filter method
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = getJwtFromReuest(request);
			boolean tokenValidation = tokenUtil.validate(token);
			long id = tokenUtil.getUserIdFromJwt(token);
			User userDetails = userServiceImpl.getByUserId(id);

			if (StringUtils.hasText(token) && tokenValidation) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Collections.emptyList());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			// logger.error("Could not set user authentication in security context", ex);

			// throw new
			// WebPortalRuntimeException(MessageCode.PORTALG003AuthinticationError);
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * <p>
	 * To get the Token from request header.
	 * 
	 * @param request
	 *            - <code>HttpServletRequest </code>
	 * @return jwt token <code> {@link String} </code>
	 */
	private String getJwtFromReuest(HttpServletRequest request) {
		String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearerToken)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
