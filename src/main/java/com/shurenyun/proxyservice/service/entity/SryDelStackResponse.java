package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SryDelStackResponse{
	
	int code;
	SryDelStackData data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public SryDelStackData getData() {
		return data;
	}
	public void setData(SryDelStackData data) {
		this.data = data;
	}
	
	

}
