package com.shurenyun.proxyservice.service.entity;

public class SryCreateStackRequest {
	
	String name;
	Object compose;
	Object marathonConfig;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getCompose() {
		return compose;
	}
	public void setCompose(Object compose) {
		this.compose = compose;
	}
	public Object getMarathonConfig() {
		return marathonConfig;
	}
	public void setMarathonConfig(Object marathonConfig) {
		this.marathonConfig = marathonConfig;
	}
	
}
