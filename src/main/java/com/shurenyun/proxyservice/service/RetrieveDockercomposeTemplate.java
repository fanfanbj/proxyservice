package com.shurenyun.proxyservice.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RetrieveDockercomposeTemplate {
	
	// Define the logger object for this class
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	public String get(String svn_url) {
		//retrieve docker-compose-template from SVN to /data/{svn_app_name}/.
		getTemplateFromSVN(svn_url);
		
		String svn_app_name = "app1";
		String template_file_name = "/data/"+svn_app_name+"/docker-compose-template.yml";
		
		//load docker-compose-template.
		String template_file = loadTemplate(template_file_name);
		
		return template_file;
	}
	
	/*
	 * retrieve docker-compose-template from SVN to /data/app1/.
	 */
	private void getTemplateFromSVN(String svn_url) {
		
	}
	
	/*
	 * load docker-compose-template.
	 */
	private String loadTemplate(String template_file_name){
	
		String  template_file = "";

		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =  new FileReader(template_file_name);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =  new BufferedReader(fileReader);

            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                template_file += line;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
           log.error("Unable to open file '" +template_file_name+ "'");                
        }
        catch(IOException ex) {
        	log.error("Error reading file '" +template_file_name+ "'");   
        }
		
		return template_file;
    }
}
