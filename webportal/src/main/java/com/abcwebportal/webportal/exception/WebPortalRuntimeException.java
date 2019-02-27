package com.abcwebportal.webportal.exception;

import com.abcwebportal.webportal.base.MessageCode;
import com.abcwebportal.webportal.base.exception.WebPortalApplicationException;

/**
 * <p>
 * Exception representing an error that has occurred while interacting with the
 * server.
 * </p>
 *
 */
public class WebPortalRuntimeException extends WebPortalApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3056218875997002859L;

	public WebPortalRuntimeException(MessageCode errorCode) {
		super(errorCode);
	}

}
