package com.shurenyun.proxyservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shurenyun.proxyservice.controller.vo.AddStackRequest;
import com.shurenyun.proxyservice.controller.vo.AddStackResponse;
import com.shurenyun.proxyservice.controller.vo.DelStackResponse;
import com.shurenyun.proxyservice.controller.vo.GetStackResponse;
import com.shurenyun.proxyservice.domain.ServiceCompose;
import com.shurenyun.proxyservice.domain.EQImage;
import com.shurenyun.proxyservice.service.CreateDabCompose;
import com.shurenyun.proxyservice.service.RetrieveDockercomposeTemplate;
import com.shurenyun.proxyservice.service.RetrieveNotOccupiedPort;
import com.shurenyun.proxyservice.service.ShurenyunApiRequestForward2;
import com.shurenyun.proxyservice.service.entity.SryCreateStackResponse;
import com.shurenyun.proxyservice.service.entity.SryDelStackResponse;
import com.shurenyun.proxyservice.service.entity.SrySearchStackResponse;

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
	RetrieveDockercomposeTemplate retrieveDockercomposeTemplate;
	
 	@Autowired
	RetrieveNotOccupiedPort retrieveNotOccupiedPort;

	@Autowired
	CreateDabCompose createDabCompose;
	
	@Autowired
	ShurenyunApiRequestForward2 shurenyunApiRequestForward2;

	@RequestMapping(
		method = RequestMethod.POST,
		value = "/stack",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public AddStackResponse create(@Valid @RequestBody AddStackRequest addStackRequest) {
		String svn_url = addStackRequest.getSvn_url();
		String stack_name = addStackRequest.getStack_name();
		List<EQImage> images = addStackRequest.getImages();
		
		String inputmessage = " svn_url:"+svn_url+
							  " stack_name:"+stack_name;
		log.debug("POST /stack "+inputmessage);
		
		//retrieve docker compose template.
		Map<String,ServiceCompose> docker_compose_template_yaml = retrieveDockercomposeTemplate.doGet(svn_url);

		//need to use lock, when retrieve free ports for services.
		Lock lock = new ReentrantLock();
		lock.lock();
		
		// TODO BEGIN.
		//get resource port. 
		List<Long> not_occupied_ports = retrieveNotOccupiedPort.getPorts(); 
		
		//create dab compose.
		createDabCompose.doCreate(docker_compose_template_yaml,images, not_occupied_ports);
		String dab = createDabCompose.getDab();
		
		//create stack.
		SryCreateStackResponse sryCreateStackResponse = shurenyunApiRequestForward2.createStack(stack_name,dab);
		
		//create AddStackResponse.
		AddStackResponse addStackResponse = new AddStackResponse();
		addStackResponse.setStatus(Integer.toString(sryCreateStackResponse.getCode()));
		addStackResponse.setError_message(sryCreateStackResponse.getData().getMessage());
		//TODO END.
		lock.unlock();
		return addStackResponse;
		
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/stack/{stack_name}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public GetStackResponse get(@PathVariable("stack_name") String stack_name ) {
		
		log.debug("GET /stack/"+stack_name);
	
		SrySearchStackResponse srySearchStackResponse = shurenyunApiRequestForward2.searchStack(stack_name);
		
		//create GetStackResponse.	
		GetStackResponse getStackResponse = new GetStackResponse();
		getStackResponse.setStatus(Integer.toString(srySearchStackResponse.getCode()));
		getStackResponse.setError_message(srySearchStackResponse.getData().getMessage());
	
		return getStackResponse;
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			value = "/stack/{stack_name}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DelStackResponse delete(@PathVariable("stack_name") String stack_name ) {
			log.debug("DELETE /stack/"+stack_name);
			
			//invoke shurenyun get stack API.
			SryDelStackResponse sryDelStackResponse = shurenyunApiRequestForward2.delStack(stack_name);
			
			//create DelStackResponse.
			DelStackResponse delStackResponse = new DelStackResponse();
			delStackResponse.setStatus(Integer.toString(sryDelStackResponse.getCode()));
			delStackResponse.setError_message(sryDelStackResponse.getData().getMessage());
		
			return delStackResponse;
	}

}
