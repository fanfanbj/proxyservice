package com.shurenyun.proxyservice.controller.vo;

import java.util.List;

import com.shurenyun.proxyservice.domain.EQApp;

public class GetStackResponse {
	
	String status;
	String stack_status;
	List<EQApp> app_list;
	String error_message;
	
	public String getStack_status() {
		return stack_status;
	}
	public void setStack_status(String stack_status) {
		this.stack_status = stack_status;
	}
	public List<EQApp> getApp_list() {
		return app_list;
	}
	public void setApp_list(List<EQApp> app_list) {
		this.app_list = app_list;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	

}
