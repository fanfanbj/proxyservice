package com.shurenyun.proxyservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shurenyun.proxyservice.controller.vo.AddStackRequest;
import com.shurenyun.proxyservice.controller.vo.AddStackResponse;
import com.shurenyun.proxyservice.controller.vo.DelStackResponse;
import com.shurenyun.proxyservice.controller.vo.GetStackResponse;
import com.shurenyun.proxyservice.domain.ServiceCompose;
import com.shurenyun.proxyservice.domain.EQApp;
import com.shurenyun.proxyservice.domain.EQImage;
import com.shurenyun.proxyservice.domain.SryApp;
import com.shurenyun.proxyservice.service.CreateDockercompose;
import com.shurenyun.proxyservice.service.RetrieveDockercomposeTemplate;
import com.shurenyun.proxyservice.service.ShurenyunApiAccess;
import com.shurenyun.proxyservice.service.ShurenyunApiRequestForward;
import com.shurenyun.proxyservice.service.entity.SryCreateStackResponse;
import com.shurenyun.proxyservice.service.entity.SryDelStackResponse;
import com.shurenyun.proxyservice.service.entity.SrySearchStackResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		
		//create docker compose and shurenyun compose.
		createDockercompose.doCreate(images, docker_compose_template_yaml);
		String dockercompose = createDockercompose.getDockercompose();
		String shurenyuncompose = createDockercompose.getShurenyunCompose();
		
		//invoke shurenyun create stack API.
		String token = shurenyunApiAccess.doAuthentication();
		SryCreateStackResponse sryCreateStackResponse = shurenyunApiRequestForward.createStack(token,cluster_id,stack_name,dockercompose,shurenyuncompose);
		
		//create AddStackResponse.
		AddStackResponse addStackResponse = new AddStackResponse();
		addStackResponse.setCluster_id(cluster_id);
		addStackResponse.setStack_id(sryCreateStackResponse.getStack_id());
		addStackResponse.setError_message(sryCreateStackResponse.getError_message());
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
		
		//create app_list.
		List<EQApp> app_list = new ArrayList<EQApp>();
		List<SryApp> SryApps = srySearchStackResponse.getApps();
		for (SryApp sryApp: SryApps) {
			String app_name = sryApp.getName();
			int status = sryApp.getStatus();
			EQApp EQApp = new EQApp();
			EQApp.setName(app_name);
			EQApp.setStatus(Integer.toString(status));
			app_list.add(EQApp);
		}
		
		//create GetStackResponse.	
		GetStackResponse getStackResponse = new GetStackResponse();
		getStackResponse.setApp_list(app_list);
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
			delStackResponse.setStatus(sryDelStackResponse.getStatus());
			delStackResponse.setError_message(sryDelStackResponse.getError_message());
			return delStackResponse;
	}

}
