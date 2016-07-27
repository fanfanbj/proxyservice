package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SryDelStackResponse {
	
	int code;
	DelStackData data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public DelStackData getData() {
		return data;
	}
	public void setData(DelStackData data) {
		this.data = data;
	}
	
	

}
