package com.shurenyun.proxyservice.controller.vo;

import java.util.List;

import com.shurenyun.proxyservice.domain.EQApp;

public class GetStackResponse {
	
	String stack_status;
	List<EQApp> app_list;
	
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
	

}
