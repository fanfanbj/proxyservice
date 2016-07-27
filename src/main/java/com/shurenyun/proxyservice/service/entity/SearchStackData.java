package com.shurenyun.proxyservice.service.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchStackData {
	
	int Id;
	int Cid;
	int Uid;
	String status;
	String name;
	String compose;
	String marathonConfig;
	List<DeployedApplication> deployedApplications;
	List<Application> applications;
	
	//error
	String message;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompose() {
		return compose;
	}
	public void setCompose(String compose) {
		this.compose = compose;
	}
	public String getMarathonConfig() {
		return marathonConfig;
	}
	public void setMarathonConfig(String marathonConfig) {
		this.marathonConfig = marathonConfig;
	}
	public List<DeployedApplication> getDeployedApplications() {
		return deployedApplications;
	}
	public void setDeployedApplications(List<DeployedApplication> deployedApplications) {
		this.deployedApplications = deployedApplications;
	}
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
