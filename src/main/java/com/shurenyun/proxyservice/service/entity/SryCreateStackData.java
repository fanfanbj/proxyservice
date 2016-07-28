package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SryCreateStackData {
	
	String stack_id;
	//error
	String message;

	public String getStack_id() {
		return stack_id;
	}

	public void setStack_id(String stack_id) {
		this.stack_id = stack_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
