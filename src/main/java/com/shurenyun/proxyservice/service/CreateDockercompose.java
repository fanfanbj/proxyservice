package com.shurenyun.proxyservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shurenyun.proxyservice.domain.ServiceCompose;
import com.shurenyun.proxyservice.util.YamlFileParser;
import com.shurenyun.proxyservice.domain.EQImage;

@Service
public class CreateDockercompose {
	
	// Define the logger object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	Map<String,ServiceCompose> services;
	String dockerCompose;
	String shurenyunCompose;

	
	public void doCreate(List<EQImage> images,
			Map<String,ServiceCompose> docker_compose_template_yaml) {
		
		//initial.
		services = docker_compose_template_yaml;
		
		//get service tag resource.
		getServiceTagResource(images);
		
		//get service port resource.
		getServicePortResource(docker_compose_template_yaml);
		
		//print service resource.
//		printServiceResource();
		
		//save service resource.
		saveServiceResource();
		
		//create shurenyun compose.
		shurenyunCompose();
		
	}
	
	/**
	 * get service tag resource.
	 * @param images
	 */
	private void getServiceTagResource(List<EQImage> images) {
		
		//create compose image name with tag.
		for (String service_name : this.services.keySet()) {
			ServiceCompose service_config = this.services.get(service_name);
			String template_image_name = service_config.getImage();
			String[] template_image_name_array = template_image_name.split(":");
			//replace template image name with user's input.
			for(EQImage image: images) {
				String image_name = image.getName();
				if(template_image_name_array[0].equals(image_name)){
					String image_tag = image.getTag();
					String compose_name = image_name+":"+image_tag;
					service_config.setImage(compose_name);
				}
			}
		}	
	}
	
	/**
	 * get service port resource.
	 * @param docker_componse_template_yaml
	 */
	private void getServicePortResource(Map<String,ServiceCompose> docker_compose_template_yaml) {
		
		//TODO get service port resource. here is Hardcode. it will replace by calling shurenyun's API.
		int not_occupy_port = 9000;
		
		//create service port map.
		Map<String,String> service_port_map = new HashMap<String,String>();
		
		//replace template port with not_occupy_port.
		for (String service_name : this.services.keySet()) {
			ServiceCompose service_config = this.services.get(service_name);
			List<String> template_ports = (List<String>)service_config.getPorts();
			List<String> ports = new ArrayList<String>();
			if(template_ports!=null) {
				for(String template_port: template_ports) {
					String template_port_tag = template_port.split(":")[0];
					ports.add(Integer.toString(not_occupy_port)+":"+Integer.toString(not_occupy_port));
					service_port_map.put(template_port_tag, Integer.toString(not_occupy_port));
					not_occupy_port++;
				}
			}
			service_config.setPorts(ports);
		}
		//replace template environment with service_port_map.
		for(String service_name :this.services.keySet()) {
			ServiceCompose service_config = this.services.get(service_name);
			List<String> template_envs = (List<String>)service_config.getEnv();
			List<String> envs = new ArrayList<String>();
			if(template_envs!=null) {
				for(String template_env: template_envs) {
					String[] template_env_array = template_env.split("=");
					//print service_port_map.
					boolean replace_flag = false;
					for(String service_port: service_port_map.keySet()) {
						//replace template port.
						if(template_env_array != null && template_env_array.length==2){
							if(template_env_array[1].trim().equals(service_port)) {
								envs.add(template_env_array[0]+": "+service_port.replace(template_env_array[1].trim(), service_port_map.get(service_port)));
								replace_flag = true;
							}
						}	
					}
					
					if (!replace_flag)
							envs.add(template_env.replace("=", ": "));
					
				}
			}	
			service_config.setEnv(envs);
		}
	}
	
	/**
	 * print service resource
	 */
	private void printServiceResource() {
		YamlFileParser yamlFileParser = new YamlFileParser();
		yamlFileParser.printYamlMap(services);
		
	}
	
	/**
	 * save service resource to yml.
	 * @param services
	 */
	private void saveServiceResource() {
		
		dockerCompose = "";
		for(String service_name: services.keySet()) {
			dockerCompose += service_name+":\n";
			ServiceCompose serviceCompose = (ServiceCompose)services.get(service_name);
			//image.
			dockerCompose += "  image: "+serviceCompose.getImage()+"\n";
			//ports.
			if(serviceCompose.getPorts()!=null) {
				dockerCompose += "  ports:\n";
				for(String port:serviceCompose.getPorts()){
					dockerCompose += "    - "+port+"\n";
				}
			}
			//links.
			if(serviceCompose.getLinks()!=null) {
				dockerCompose += "  links:\n";
				for(String link:serviceCompose.getLinks()){
					dockerCompose += "    - "+link+"\n";
				}
			}	
			//environment.
			if(serviceCompose.getEnv()!=null) {
				dockerCompose += "  environment:\n"; 
				for(String env:serviceCompose.getEnv()){
					dockerCompose += "    "+env+"\n";
				}
			}	
			//volume.
			if(serviceCompose.getVolumes()!=null) {
				dockerCompose += "  volumes:\n";
				for(String volume:serviceCompose.getVolumes()){
					dockerCompose += "    - "+volume+"\n";
				}
			}	
		}
		log.debug(dockerCompose);
	}
	
	/**
	 * create shurenyun compose.
	 * @param services
	 */
	private void shurenyunCompose() {
		
		shurenyunCompose = "";
		for(String service_name: services.keySet()) {
			shurenyunCompose += service_name+":\n";
			shurenyunCompose += "  cpu: 0.2\n";
			shurenyunCompose += "  mem: 512\n";
			shurenyunCompose += "  instances: 1\n";
		}
		log.debug(shurenyunCompose);
	}

	/**
	 * get services.
	 * @return
	 */
	public Map<String, ServiceCompose> getServices() {
		return services;
	}
	/**
	 * set services.
	 * @param services
	 */
	public void setServices(Map<String, ServiceCompose> services) {
		this.services = services;
	}

	public String getDockerCompose() {
		return dockerCompose;
	}

	public void setDockerCompose(String dockerCompose) {
		this.dockerCompose = dockerCompose;
	}

	public String getShurenyunCompose() {
		return shurenyunCompose;
	}

	public void setShurenyunCompose(String shurenyunCompose) {
		this.shurenyunCompose = shurenyunCompose;
	}
	
	

	
}
