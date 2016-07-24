package com.shurenyun.proxyservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shurenyun.proxyservice.controller.vo.AddStackRequest;
import com.shurenyun.proxyservice.controller.vo.AddStackResponse;
import com.shurenyun.proxyservice.controller.vo.DelStackResponse;
import com.shurenyun.proxyservice.controller.vo.GetStackResponse;
import com.shurenyun.proxyservice.domain.EQImage;
import com.shurenyun.proxyservice.service.CreateDockercompose;
import com.shurenyun.proxyservice.service.RetrieveAppDynamicResource;
import com.shurenyun.proxyservice.service.RetrieveDockercomposeTemplate;
import com.shurenyun.proxyservice.service.ShurenyunApiAccess;
import com.shurenyun.proxyservice.service.ShurenyunApiRequestForward;

import java.util.List;

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
	RetrieveAppDynamicResource retrieveAppDynamicResource;
	
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
		//List<EQImage> images = addStackRequest.getImages();
		
		String inputmessage = " svn_url:"+svn_url+
							  " stack_name:"+stack_name+
							  " cluster_id:"+cluster_id;
		log.debug("POST /stack "+inputmessage);
		//retrieve docker-compose-template.yml
		String template_file = retrieveDockercomposeTemplate.get(svn_url);
		log.debug(template_file);
		
		AddStackResponse addStackResponse = new AddStackResponse();
		
		return addStackResponse;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/cluster/{cluster_id}/stack/{stack_id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
		public GetStackResponse get(@PathVariable("cluster_id") String cluster_id,
				          @PathVariable("stack_id") String stack_id ) {
		log.debug("GET /cluster/"+cluster_id+"/stack/"+stack_id);
		GetStackResponse getStackResponse = new GetStackResponse();
		
		return getStackResponse;
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			value = "/cluster/{cluster_id}/stack/{stack_id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
		public DelStackResponse delete(@PathVariable("cluster_id") String cluster_id,
					          @PathVariable("stack_id") String stack_id ) {
			log.debug("DELETE /cluster/"+cluster_id+"/stack/"+stack_id);
			DelStackResponse delStackResponse = new DelStackResponse();
		
			return delStackResponse;
		}

}
