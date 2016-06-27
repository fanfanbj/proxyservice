package com.shurenyun.proxyservice.controller.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ReturnResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4888510146002336270L;
	private Boolean result;
	private String message;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private T contents;
	

	/**
	 * @return the result
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Boolean result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getContents() {
		return contents;
	}

	public void setContents(T contents) {
		this.contents = contents;
	}

	
}
