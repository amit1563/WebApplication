package com.abcwebportal.webportal.exceptionhandler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abcwebportal.webportal.base.exception.ExceptionDetail;
import com.abcwebportal.webportal.exception.WebPortalRuntimeException;

/**
 * To handle all type of exception extends
 * {@link ResponseEntityExceptionHandler}
 *
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	private MessageSource messageSource;

	@Autowired
	public ExceptionHandler(MessageSource messageSource) {
		this.setMessageSource(messageSource);
	}

	/**
	 * 
	 * @param ex
	 *            - WebPortalRuntimeException
	 * @return {@link ResponseEntity} - will return exception Detail binded with
	 *         {@link ResponseEntity}
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(WebPortalRuntimeException.class)
	public ResponseEntity<ExceptionDetail> handleException(WebPortalRuntimeException ex) {
		String exceptionMessage = ex.getMessage();

		return new ResponseEntity<ExceptionDetail>(new ExceptionDetail(new Date(), exceptionMessage, ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	private void setMessageSource(MessageSource messageSource2) {
		this.messageSource = messageSource;
	}
}
