package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SryCreateStackResponse {
	
	int code;
	CreateStackData data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public CreateStackData getData() {
		return data;
	}
	public void setData(CreateStackData data) {
		this.data = data;
	}
	
	
	

}
