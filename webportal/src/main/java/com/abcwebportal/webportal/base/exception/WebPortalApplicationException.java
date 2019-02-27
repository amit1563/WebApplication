package com.abcwebportal.webportal.base.exception;

import com.abcwebportal.webportal.base.MessageCode;

/**
 * Base class for all Webportal Application exceptions.
 */
public class WebPortalApplicationException extends RuntimeException implements WebPortalException {
	/**
	* 
	*/
	private static final long serialVersionUID = 2258401709295911945L;
	public MessageCode messageCode;

	public WebPortalApplicationException(MessageCode errorCode) {
		MessageCode.init(MessageCode.class);
		messageCode = errorCode;
	}

	@Override
	public String getMessage() {
		return MessageCode.allCodes.get(messageCode);
	}

}
