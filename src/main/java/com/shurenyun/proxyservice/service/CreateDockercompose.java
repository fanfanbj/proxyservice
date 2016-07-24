package com.shurenyun.proxyservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shurenyun.proxyservice.domain.DockerCompose;
import com.shurenyun.proxyservice.domain.EQImage;
import com.shurenyun.proxyservice.domain.ShurenyunCompose;

@Service
public class CreateDockercompose {
	
	List<DockerCompose> dockercompose;
	List<ShurenyunCompose> shurenyunCompose;

	public void doCreate(List<EQImage> images,
					Map<String, Map<String,List<String>>> docker_compose_template_yaml) {
		
		//initial.
		dockercompose = new ArrayList<DockerCompose>();
		shurenyunCompose = new ArrayList<ShurenyunCompose>();
		
		//get service tag resource.
		getServiceTagResource(images);
		
		//get service port resource.
		getServicePortResource(docker_compose_template_yaml);
		
		//save service resource.
		saveServiceResource(dockercompose);
		
		//create shurenyun compose.
		shurenyunCompose(dockercompose);
		
	}
	
	/**
	 * get service tag resource.
	 * @param images
	 */
	private void getServiceTagResource(List<EQImage> images) {
		//create compose image name.
		for(EQImage image: images) {
	
			String image_name = image.getName();
			String image_tag = image.getTag();
			String compose_name = image_name+":"+image_tag;
			DockerCompose dockerCompose = new DockerCompose();
			dockerCompose.setImage(compose_name);
			this.dockercompose.add(dockerCompose);
		
		}
	}
	
	/**
	 * get service port resource.
	 * @param docker_componse_template_yaml
	 */
	private void getServicePortResource(Map<String, Map<String,List<String>>> docker_compose_template_yaml) {
		
		//
		
	}
	
	/**
	 * save service resource.
	 * @param services
	 */
	private void saveServiceResource(List<DockerCompose> services) {
		
	}
	
	/**
	 * create shurenyun compose.
	 * @param services
	 */
	private void shurenyunCompose(List<DockerCompose> services) {
		
	}

	/**
	 * get docker compose.
	 * @return
	 */
	public List<DockerCompose> getDockercompose() {
		return dockercompose;
	}
	/**
	 * set docker compose.
	 * @param dockercompose
	 */
	public void setDockercompose(List<DockerCompose> dockercompose) {
		this.dockercompose = dockercompose;
	}

	/**
	 * get shurenyun compose.
	 * @return
	 */
	public List<ShurenyunCompose> getShurenyunCompose() {
		return shurenyunCompose;
	}
	/**
	 * set shurenyun compose.
	 * @param shurenyunCompose
	 */
	public void setShurenyunCompose(List<ShurenyunCompose> shurenyunCompose) {
		this.shurenyunCompose = shurenyunCompose;
	}
	
}
