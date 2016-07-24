package com.shurenyun.proxyservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class YamlFileParser {

	// Define the logger object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * read from docker-compose.yml
	 * @param yaml_file_name
	 * @return
	 */
	public Map<String, Map<String,List<String>>> readFromFile(String yaml_file_name) {	
		
		File yaml_file = new File(yaml_file_name);
		InputStream is = null;
		try {
			is = new FileInputStream(yaml_file);
			Yaml yaml = new Yaml();
			@SuppressWarnings("unchecked")
			Map<String, Map<String,List<String>>> service_yaml  = (Map<String, Map<String,List<String>>>) yaml.load(is);
			is.close();
			return service_yaml;
		} catch (FileNotFoundException e) {
			log.error("Unable to open file '" +yaml_file_name+ "'");      
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Error reading file '" +yaml_file_name+ "'"); 
			e.printStackTrace();
		}
		return null;
	}


}
