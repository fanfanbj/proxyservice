package com.shurenyun.proxyservice.service.entity;

import java.util.List;

public class SearchData {
	
	int id;
	int cid;
	int uid;
	String status;
	String name;
	String compose;
	String marathonConfig;
	List<DeployedApplication> deployedApplications;
	List<Application> applications;
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

	
}
