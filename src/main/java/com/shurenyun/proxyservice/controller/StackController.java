package com.shurenyun.proxyservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shurenyun.proxyservice.service.entity.SryAppStatus;
import com.shurenyun.proxyservice.service.entity.SryAppStatusResponse;
import com.shurenyun.proxyservice.service.entity.SryApplication;
import com.shurenyun.proxyservice.controller.vo.AddStackRequest;
import com.shurenyun.proxyservice.controller.vo.AddStackResponse;
import com.shurenyun.proxyservice.controller.vo.DelStackResponse;
import com.shurenyun.proxyservice.controller.vo.GetStackResponse;
import com.shurenyun.proxyservice.domain.ServiceCompose;
import com.shurenyun.proxyservice.domain.EQApp;
import com.shurenyun.proxyservice.domain.EQImage;
import com.shurenyun.proxyservice.service.CreateDockercompose;
import com.shurenyun.proxyservice.service.RetrieveDockercomposeTemplate;
import com.shurenyun.proxyservice.service.RetrieveNotOccupiedPort;
import com.shurenyun.proxyservice.service.ShurenyunApiAccess;
import com.shurenyun.proxyservice.service.ShurenyunApiRequestForward;
import com.shurenyun.proxyservice.service.entity.SryDeployedApplication;
import com.shurenyun.proxyservice.service.entity.SryInnerPort;
import com.shurenyun.proxyservice.service.entity.SryOccupiedPortResponse;
import com.shurenyun.proxyservice.service.entity.SryOuterPort;
import com.shurenyun.proxyservice.service.entity.SryCreateStackResponse;
import com.shurenyun.proxyservice.service.entity.SryDelStackResponse;
import com.shurenyun.proxyservice.service.entity.SrySearchStackResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class StackController {
	
	// Define the logger object for this class
 	private final Logger log = LoggerFactory.getLogger(this.getClass());
 	
 	@Autowired
	RetrieveNotOccupiedPort retrieveNotOccupiedPort;

	@Autowired
	RetrieveDockercomposeTemplate retrieveDockercomposeTemplate;
	
	@Autowired
	CreateDockercompose createDockercompose;
	
	@Autowired
	ShurenyunApiAccess shurenyunApiAccess;
	
	@Autowired
	ShurenyunApiRequestForward shurenyunApiRequestForward;

	@RequestMapping(
		method = RequestMethod.POST,
		value = "/stack",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public AddStackResponse create(@Valid @RequestBody AddStackRequest addStackRequest) {
		String svn_url = addStackRequest.getSvn_url();
		String stack_name = addStackRequest.getStack_name();
		String cluster_id = addStackRequest.getCluster_id();
		List<EQImage> images = addStackRequest.getImages();
		
		String inputmessage = " svn_url:"+svn_url+
							  " stack_name:"+stack_name+
							  " cluster_id:"+cluster_id;
		log.debug("POST /stack "+inputmessage);
		
		//retrieve docker compose template.
		Map<String,ServiceCompose> docker_compose_template_yaml = retrieveDockercomposeTemplate.doGet(svn_url);
		
		//invoke shurenyun create stack API.
		String token = shurenyunApiAccess.doAuthentication();

		//need to use lock, when retrieve free ports for services.
		Lock lock = new ReentrantLock();
		lock.lock();
		
		//get resource port. 
		List<Long> not_occupied_ports = retrieveNotOccupiedPort.getPorts(token, cluster_id); 
		
		//create docker compose and shurenyun compose.
		createDockercompose.doCreate(docker_compose_template_yaml,images, not_occupied_ports);
		String dockercompose = createDockercompose.getDockerCompose();
		String shurenyuncompose = createDockercompose.getShurenyunCompose();
	
		//print request message.
		String requestforward = "curl -X POST --header \"Content-Type: application/json\" --header \"Accept: application/json\""+
								"--header \"Authorization: "+token+"\" -d \"{"+
								"\"name\": \""+stack_name+"\","+
								"\"compose\":  \""+dockercompose+"\"," +
								"\"marathonConfig\": \""+shurenyuncompose+"\"" +
								"}";
		log.debug(requestforward);
		
		//create stack.
		SryCreateStackResponse sryCreateStackResponse = shurenyunApiRequestForward.createStack(token,cluster_id,stack_name,dockercompose,shurenyuncompose);
		lock.unlock();
		
		//create AddStackResponse.
		AddStackResponse addStackResponse = new AddStackResponse();
		addStackResponse.setCluster_id(cluster_id);
		addStackResponse.setStack_id(sryCreateStackResponse.getData().getStack_id());
		return addStackResponse;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/cluster/{cluster_id}/stack/{stack_id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public GetStackResponse get(@PathVariable("cluster_id") String cluster_id,
				          @PathVariable("stack_id") String stack_id ) {
		log.debug("GET /cluster/"+cluster_id+"/stack/"+stack_id);
	
		//invoke shurenyun get stack API.
		String token = shurenyunApiAccess.doAuthentication();
		SrySearchStackResponse srySearchStackResponse = shurenyunApiRequestForward.searchStack(token,cluster_id,stack_id);
		
		List<SryDeployedApplication> deployedApplications = srySearchStackResponse.getData().getDeployedApplications();
		List<SryApplication> applications = srySearchStackResponse.getData().getApplications();

//		Debug code.
//		ObjectMapper mapper = new ObjectMapper();
//		
//		//Object to JSON in String
//		String jsonInString;
//		try {
//			jsonInString = mapper.writeValueAsString(deployedApplication);
//			log.debug(jsonInString);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//create app_list.
		List<EQApp> app_list = new ArrayList<EQApp>();
		for(SryDeployedApplication deployedApplication:deployedApplications){
			int id = deployedApplication.getId();
			String name = deployedApplication.getName();
			EQApp eqapp = new EQApp();
			eqapp.setId(id);
			eqapp.setName(name);
			eqapp.setStatus("deployed");
			app_list.add(eqapp);
		}
		
		for(SryApplication application:applications){
			String name = application.getName();
			EQApp eqapp = new EQApp();
			eqapp.setName(name);
			if(!app_list.contains(eqapp)){
				app_list.add(eqapp);
			}
		}
		
		//get App status.
		for(EQApp eqapp:app_list) {
			int id = eqapp.getId();
			if(id != 0) {
				int app_id = id;
				SryAppStatusResponse sryAppStatusResponse = shurenyunApiRequestForward.getAppStatus(token, Long.parseLong(cluster_id), app_id);
				int status_code =  sryAppStatusResponse.getData().getStatus();
				eqapp.setStatus(SryAppStatus.fromStatusId(status_code));
			
			}
		}
		
		//create GetStackResponse.	
		GetStackResponse getStackResponse = new GetStackResponse();
		getStackResponse.setApp_list(app_list);
		getStackResponse.setStatus(Integer.toString(srySearchStackResponse.getCode()));
		getStackResponse.setError_message(srySearchStackResponse.getData().getMessage());
		return getStackResponse;
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			value = "/cluster/{cluster_id}/stack/{stack_id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DelStackResponse delete(@PathVariable("cluster_id") String cluster_id,
					          @PathVariable("stack_id") String stack_id ) {
			log.debug("DELETE /cluster/"+cluster_id+"/stack/"+stack_id);
			
			//invoke shurenyun get stack API.
			String token = shurenyunApiAccess.doAuthentication();
			SryDelStackResponse sryDelStackResponse = shurenyunApiRequestForward.delStack(token,cluster_id,stack_id);
			
			//create DelStackResponse.
			DelStackResponse delStackResponse = new DelStackResponse();
			delStackResponse.setStatus(Integer.toString(sryDelStackResponse.getCode()));
			delStackResponse.setError_message(sryDelStackResponse.getData().getMessage());
			return delStackResponse;
	}

}
