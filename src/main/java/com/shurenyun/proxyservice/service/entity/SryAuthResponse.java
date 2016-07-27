package com.shurenyun.proxyservice.service.entity;

public class SryAuthResponse {

	private AuthData data;
	private int code;
	
	public AuthData getData() {
		return data;
	}
	public void setData(AuthData data) {
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
