package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeployedApplication {
	
	int id;
	int cid;
	int uid;
	int stackId;
	int instances;
	String name;
	String dependencies;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getStackId() {
		return stackId;
	}
	public void setStackId(int stackId) {
		this.stackId = stackId;
	}
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
	public String getDependencies() {
		return dependencies;
	}
	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}
	
	

}
