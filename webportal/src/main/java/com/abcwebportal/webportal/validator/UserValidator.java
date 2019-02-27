package com.abcwebportal.webportal.validator;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.abcwebportal.webportal.model.user.User;

/**
 * UserValidator - will validate the user object before proceeding to persist
 */

@Component
public class UserValidator {

	HashMap<String, String> ValidationErrorMap = new HashMap<>();

	public HashMap<String, String> getValidationErrorMap() {
		return ValidationErrorMap;
	}

	public void setValidationErrorMap(HashMap<String, String> validationErrorMap) {
		ValidationErrorMap = validationErrorMap;
	}

	public void ValidationErrorMap() {

	}

	public void validate(Object object) {
		User user = (User) object;
		ValidationErrorMap.clear();
		if (user.getPassword().length() < 6) {
			ValidationErrorMap.put("password", "Password must be at least 6 characters");
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			ValidationErrorMap.put("confirmPassword", "Password must match");
		}
	}

}
