package com.shurenyun.proxyservice.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeployedApplication {
	
	int Id;
	int Cid;
	int Uid;
	int StackId;
	int Instances;
	String Name;
	String Dependencies;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getCid() {
		return Cid;
	}
	public void setCid(int cid) {
		Cid = cid;
	}
	public int getUid() {
		return Uid;
	}
	public void setUid(int uid) {
		Uid = uid;
	}
	public int getStackId() {
		return StackId;
	}
	public void setStackId(int stackId) {
		StackId = stackId;
	}
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
	public String getDependencies() {
		return Dependencies;
	}
	public void setDependencies(String dependencies) {
		Dependencies = dependencies;
	}
	
	

}
