package com.shurenyun.proxyservice.domain;

import javax.persistence.*;

public class ServicePort {
	String service_id;
	String port;
	
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
}
