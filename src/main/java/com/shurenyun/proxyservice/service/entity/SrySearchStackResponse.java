package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SrySearchStackResponse{
	
	int code;
	SrySearchStackData data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public SrySearchStackData getData() {
		return data;
	}
	public void setData(SrySearchStackData data) {
		this.data = data;
	}
	
}
