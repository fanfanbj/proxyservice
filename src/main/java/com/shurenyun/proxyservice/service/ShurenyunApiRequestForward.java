package com.shurenyun.proxyservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shurenyun.proxyservice.service.entity.SryAppStatusResponse;
import com.shurenyun.proxyservice.service.entity.SryCreateStackRequest;
import com.shurenyun.proxyservice.service.entity.SryCreateStackResponse;
import com.shurenyun.proxyservice.service.entity.SryDelStackResponse;
import com.shurenyun.proxyservice.service.entity.SryOccupiedPortResponse;
import com.shurenyun.proxyservice.service.entity.SrySearchStackResponse;
import com.shurenyun.proxyservice.util.ServiceProperties;

@Service
public class ShurenyunApiRequestForward {
	
	// Define the logger object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	  	
	@Autowired
	private ServiceProperties configuration;
	
	/**
	 * create stack.
	 * @param token
	 * @param cluster_id
	 * @param stack_name
	 * @param dockercompose
	 * @param shurenyuncompose
	 */
	public SryCreateStackResponse createStack(String token, String cluster_id,String stack_name,
					String dockercompose,
					String shurenyuncompose){

		RestTemplate createStackRestTemplate = new RestTemplate();
		createStackRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		createStackRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        
		String uri = new String(this.configuration.getApi()+"/clusters/"+cluster_id+"/stacks");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", token);
		
		SryCreateStackRequest sryCreateStackRequest = new SryCreateStackRequest();
		sryCreateStackRequest.setName(stack_name);
		sryCreateStackRequest.setCompose(dockercompose);
		sryCreateStackRequest.setMarathonConfig(shurenyuncompose);
		
		HttpEntity<SryCreateStackRequest> request = new HttpEntity<SryCreateStackRequest>(sryCreateStackRequest,requestHeaders);
		ResponseEntity<SryCreateStackResponse> responseEntity = createStackRestTemplate.exchange(uri, HttpMethod.POST, request, SryCreateStackResponse.class);
		SryCreateStackResponse sryCreateStackResponse = responseEntity.getBody();
		
		return sryCreateStackResponse;
	}
	
	/**
	 * search stack.
	 * @param token
	 * @param cluster_id
	 * @param stack_id
	 */
	public SrySearchStackResponse searchStack(String token, String cluster_id, String stack_id) {
		
		RestTemplate searchStackRestTemplate = new RestTemplate();
		searchStackRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		searchStackRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        
		String uri = new String(this.configuration.getApi()+"/clusters/"+cluster_id+"/stacks/"+stack_id);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", token);
	
		HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
		ResponseEntity<SrySearchStackResponse> responseEntity = searchStackRestTemplate.exchange(uri, HttpMethod.GET, request, SrySearchStackResponse.class);
		
		SrySearchStackResponse srySearchStackResponse = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		
		//DEBUG code. Object to JSON in String
		//log.debug(searchStackRestTemplate.exchange(uri, HttpMethod.GET, request,String.class).getBody());
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(searchStackRestTemplate.exchange(uri, HttpMethod.GET, request, SrySearchStackResponse.class));
			log.debug(jsonInString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return srySearchStackResponse;
	
	}
	
	/**
	 * delete stack.
	 * @param token
	 * @param cluster_id
	 * @param stack_id
	 */
	public SryDelStackResponse delStack(String token, String cluster_id, String stack_id) {
		
		RestTemplate delStackRestTemplate = new RestTemplate();
		delStackRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		delStackRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        
		String uri = new String(this.configuration.getApi()+"/clusters/"+cluster_id+"/stacks/"+stack_id);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", token);
	
		HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
		ResponseEntity<SryDelStackResponse> responseEntity = delStackRestTemplate.exchange(uri, HttpMethod.DELETE, request, SryDelStackResponse.class);
		SryDelStackResponse sryDelStackResponse = responseEntity.getBody();
		return sryDelStackResponse;
		
	}
	
	/**
	 * get occuped ports.
	 * @param token
	 * @param cluster_id
	 * @return
	 */
	public SryOccupiedPortResponse getOccupedPorts(String token,String cluster_id) {
		
		RestTemplate sryOccupiedPortRestTemplate = new RestTemplate();
		sryOccupiedPortRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		sryOccupiedPortRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        
		String uri = new String(this.configuration.getApi()+"/clusters/"+cluster_id+"/ports");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", token);
	
		HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
		
		ResponseEntity<SryOccupiedPortResponse> responseEntity = sryOccupiedPortRestTemplate.exchange(uri, HttpMethod.GET, request, SryOccupiedPortResponse.class);
		SryOccupiedPortResponse sryOccupiedPortResponse = responseEntity.getBody();
		return sryOccupiedPortResponse;
		
	} 
	
	/**
	 * get App Status.
	 * @param token
	 * @param app_id
	 * @return
	 */
	public SryAppStatusResponse getAppStatus(String token, long cluster_id, long app_id) {
		
		RestTemplate sryAppStatusRestTemplate = new RestTemplate();
		sryAppStatusRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		sryAppStatusRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        
		String uri = new String(this.configuration.getApi()+"/clusters/"+cluster_id+"/apps/"+app_id+"/status");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization", token);
	
		HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
		
		ResponseEntity<SryAppStatusResponse> responseEntity = sryAppStatusRestTemplate.exchange(uri, HttpMethod.GET, request, SryAppStatusResponse.class);
		SryAppStatusResponse sryAppStatusResponse = responseEntity.getBody();
		return sryAppStatusResponse;
		
	}
	
	

}
