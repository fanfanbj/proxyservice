package com.shurenyun.proxyservice.repositories;

import com.shurenyun.proxyservice.domain.Service;

public interface ServiceRepository {//extends JpaRepository<Service, Long> {

	public Service findByService_nameAndImage_name(String service_name,String image_name);
	
}