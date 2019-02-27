package com.abcwebportal.webportal.base.exception;

import java.util.Date;

import com.abcwebportal.webportal.base.MessageCode;

/**
 * 
 * To Translate the exception with details.
 *
 */
public class ExceptionDetail {

	private Date timeStamp;
	private String message;
	private String details;

	public static MessageCode portalMessageCode;

	public ExceptionDetail(MessageCode portalMessageCode, String message, String details) {
		super();

		this.message = message;
		this.details = details;
	}

	public ExceptionDetail(Date timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
