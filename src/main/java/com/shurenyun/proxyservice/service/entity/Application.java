package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {
	
	int instances;
	String name;
	
	public int getInstances() {
		return instances;
	}
	public void setInstances(int instances) {
		this.instances = instances;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
