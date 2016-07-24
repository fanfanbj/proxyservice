package com.shurenyun.proxyservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shurenyun.proxyservice.domain.ServicePort;

public interface ServicePortRepository extends JpaRepository<ServicePort, Long> {
	
	@Query("select max(port) from service_port")
	ServicePort findMaxServicePort();

}
