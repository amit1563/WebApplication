package com.abcwebportal.webportal.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcwebportal.webportal.base.MessageCode;
import com.abcwebportal.webportal.base.error.validationerrorhandler.FieldErrorHandler;
import com.abcwebportal.webportal.exception.WebPortalRuntimeException;
import com.abcwebportal.webportal.model.user.User;
import com.abcwebportal.webportal.services.UserService;
import com.abcwebportal.webportal.validator.UserValidator;

import io.swagger.annotations.ApiOperation;

/**
 * Rest Controller to handle {@link User} related enpoints
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	FieldErrorHandler fieldErrorHandler;
	@Autowired
	UserValidator userValidator;

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
