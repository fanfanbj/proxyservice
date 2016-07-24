package com.shurenyun.proxyservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shurenyun.proxyservice.domain.DockerCompose;
import com.shurenyun.proxyservice.domain.ShurenyunCompose;
import com.shurenyun.proxyservice.service.entity.SryCreateStackResponse;
import com.shurenyun.proxyservice.service.entity.SryDelStackResponse;
import com.shurenyun.proxyservice.service.entity.SrySearchStackResponse;

@Service
public class ShurenyunApiRequestForward {
	
	/**
	 * create stack.
	 * @param token
	 * @param cluster_id
	 * @param stack_name
	 * @param dockercompose
	 * @param shurenyuncompose
	 */
	public SryCreateStackResponse createStack(String token, String cluster_id,String stack_name,
					List<DockerCompose> dockercompose,
					List<ShurenyunCompose> shurenyuncompose){
		SryCreateStackResponse createStackResponse = new SryCreateStackResponse();
		return createStackResponse;
	}
	
	/**
	 * search stack.
	 * @param token
	 * @param cluster_id
	 * @param stack_id
	 */
	public SrySearchStackResponse searchStack(String token, String cluster_id, String stack_id) {
		SrySearchStackResponse srySearchStackResponse = new SrySearchStackResponse();
		return srySearchStackResponse;
	}
	
	/**
	 * delete stack.
	 * @param token
	 * @param cluster_id
	 * @param stack_id
	 */
	public SryDelStackResponse delStack(String token, String cluster_id, String stack_id) {
		SryDelStackResponse sryDelStackResponse = new SryDelStackResponse();
		return sryDelStackResponse;
	}
	

}
