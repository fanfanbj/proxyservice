package com.shurenyun.proxyservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "service_port")
public class ServicePort {
	@Id
	private long service_id;
	private String port;
	
	public long getService_id() {
		return service_id;
	}
	public void setService_id(long service_id) {
		this.service_id = service_id;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
