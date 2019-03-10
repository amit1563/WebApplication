package com.abcwebportal.webportal.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcwebportal.webportal.base.MessageCode;
import com.abcwebportal.webportal.base.error.validationerrorhandler.FieldErrorHandler;
import com.abcwebportal.webportal.exception.WebPortalRuntimeException;
import com.abcwebportal.webportal.model.user.User;
import com.abcwebportal.webportal.payload.JWTLoginSuccessResponse;
import com.abcwebportal.webportal.payload.UserLoginRequest;
import com.abcwebportal.webportal.security.SecurityConstants;
import com.abcwebportal.webportal.security.jwt.JwtTokenUtil;
import com.abcwebportal.webportal.services.UserService;
import com.abcwebportal.webportal.validator.UserValidator;

import io.swagger.annotations.ApiOperation;

/**
 * Rest Controller to handle {@link User} related enpoints
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	FieldErrorHandler fieldErrorHandler;
	@Autowired
	UserValidator userValidator;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	/**
	 * <p>
	 * Sequence of methods which is going to be called for authentication
	 * </p>
	 * 
	 * <code>UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken
	 * AbstractAuthenticationToken implements Authentication </code>,<br>
	 * reason we are passing UsernamePasswordAuthenticationToken to the
	 * <code>authenticationManager.authenticate Authentication
	 * authenticate(Authentication authentication) throws AuthenticationException; </code>
	 * <p>
	 * Authentication org.springframework
	 * .security.authentication.ProviderManager.authenticate(Authentication
	 * authentication) throws AuthenticationException
	 * <p>
	 * org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider. authenticate(Authentication
	 * authentication) throws AuthenticationException
	 * <p>
	 * <code>UserDetails
	 * org.springframework.security.authentication.dao.DaoAuthenticationProvider.
	 * retrieveUser(String username, UsernamePasswordAuthenticationToken
	 * authentication) throws AuthenticationException </code>
	 * 
	 */

	@PostMapping("/login")
	ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest, BindingResult result) {

		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		Authentication authentication;
		String token = null;

		if (errorMap != null) {
			return errorMap;
		}
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					userLoginRequest.getUsername(), userLoginRequest.getPassword()));
		} catch (Exception ex) {
			throw new WebPortalRuntimeException(MessageCode.PORTALG004InvalidCredential);
		}

		if (authentication != null)
			token = SecurityConstants.TOKEN_PREFIX + jwtTokenUtil.generateToken(authentication);

		return new ResponseEntity<JWTLoginSuccessResponse>(new JWTLoginSuccessResponse(true, token), HttpStatus.OK);
	}

	/**
	 * 
	 * @param user
	 *            - user object need to be ersisted
	 * @param result
	 *            - BindingResult instance
	 * @throws WebPortalRuntimeException
	 *             while persisting with proper cause
	 * @return errorMap - In case there is validation error else user object
	 *         persisted successfuly.
	 * 
	 */
	@RequestMapping(value = "/register")
	@ApiOperation(value = "1", protocols = "http", notes = " This endpoint is for user regestration ")
	public ResponseEntity<?> signup(@Valid @RequestBody User user, BindingResult result) {
		ResponseEntity<?> errorMap = fieldErrorHandler.mapValidationError(result);
		userValidator.validate(user);
		User userObj;
		if (errorMap != null) {
			return errorMap;
		}
		if (!userValidator.getValidationErrorMap().isEmpty()) {
			return new ResponseEntity<Map<String, String>>(userValidator.getValidationErrorMap(),
					HttpStatus.BAD_REQUEST);
		}
		try {
			userObj = service.save(user);

		} catch (WebPortalRuntimeException wEx) {
			throw new WebPortalRuntimeException(MessageCode.PORTALG002UserExist);
		}
		return new ResponseEntity<User>(userObj, HttpStatus.CREATED);
	}

}
