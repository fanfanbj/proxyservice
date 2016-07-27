package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SrySearchStackResponse {
	
	int code;
	SearchStackData data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public SearchStackData getData() {
		return data;
	}
	public void setData(SearchStackData data) {
		this.data = data;
	}
	
}
