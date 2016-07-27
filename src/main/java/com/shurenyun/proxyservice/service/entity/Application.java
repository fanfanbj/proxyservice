package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {
	
	int Instances;
	String Name;
	
	public int getInstances() {
		return Instances;
	}
	public void setInstances(int instances) {
		Instances = instances;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
}
