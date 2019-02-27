package com.abcwebportal.webportal.base.error.validationerrorhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * <p>
 * To handle filed errors while submitting form
 * </p>
 * 
 */
@Service
public class FieldErrorHandler {
	/**
	 * 
	 * @param result
	 *            - BindingResult
	 * @return ResponseEntity<Map<String, String>> - (errorMap,
	 *         HttpStatus.BAD_REQUEST) if there is filed validation error or null.
	 */
	public ResponseEntity<?> mapValidationError(BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError fieldError : result.getFieldErrors()) {
				errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		return null;
	}
}
