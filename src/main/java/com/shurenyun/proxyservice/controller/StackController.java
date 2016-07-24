package com.shurenyun.proxyservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shurenyun.proxyservice.controller.vo.AddStackRequest;
import com.shurenyun.proxyservice.controller.vo.AddStackResponse;
import com.shurenyun.proxyservice.controller.vo.DelStackResponse;
import com.shurenyun.proxyservice.controller.vo.GetStackResponse;
import com.shurenyun.proxyservice.controller.vo.ReturnResult;
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
@RequestMapping("/stack")
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
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnResult<AddStackResponse> create(@Valid @RequestBody AddStackRequest addStackRequest) {
		String svn_url = addStackRequest.getSvn_url();
		String stack_name = addStackRequest.getStack_name();
		String cluster_id = addStackRequest.getCluster_id();
		List<EQImage> images = addStackRequest.getImages();
		
		String inputmessage = "svn_url:"+svn_url+
							  "stack_name:"+stack_name+
							  "cluster_id:"+cluster_id+
							  "images:"+images.get(0).getName();
		log.debug("inputmessage:"+inputmessage);
		
		ReturnResult<AddStackResponse> returnResult = new ReturnResult<AddStackResponse>();
	//	returnResult.setMessage(inputmessage);
		
		return returnResult;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/cluster/(cluster_id}/stack/{stack_id}")
		public ReturnResult<GetStackResponse> get(@PathVariable("cluster_id") String cluster_id,
				          @PathVariable("stack_id") String stack_id ) {
		ReturnResult<GetStackResponse> returnResult = new ReturnResult<GetStackResponse>();
		
		return returnResult;
	}
	
	@RequestMapping(
			method = RequestMethod.DELETE,
			value = "/cluster/(cluster_id}/stack/{stack_id}")
		public ReturnResult<DelStackResponse> delete(@PathVariable("cluster_id") String cluster_id,
					          @PathVariable("stack_id") String stack_id ) {
			ReturnResult<DelStackResponse> returnResult = new ReturnResult<DelStackResponse>();
		
			return returnResult;
		}

}
