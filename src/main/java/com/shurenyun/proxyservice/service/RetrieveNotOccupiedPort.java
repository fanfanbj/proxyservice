package com.shurenyun.proxyservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shurenyun.proxyservice.service.entity.SryInnerPort;
import com.shurenyun.proxyservice.service.entity.SryOccupiedPortResponse;
import com.shurenyun.proxyservice.service.entity.SryOuterPort;

@Service
public class RetrieveNotOccupiedPort {
	
	@Autowired
	ShurenyunApiRequestForward shurenyunApiRequestForward;
	
	//get resource port. default from 7000 to 9999. and call shurenyun's api to decide if occupied.
	public List<Long> getPorts(String token,String cluster_id) {
		List<Long> not_occupied_ports = new ArrayList<Long>();
		SryOccupiedPortResponse sryOccupiedPortResponse = shurenyunApiRequestForward.getOccupedPorts(token, cluster_id);
		List<SryInnerPort> innerPorts = sryOccupiedPortResponse.getData().getInnerPorts();
		List<SryOuterPort> outerPorts = sryOccupiedPortResponse.getData().getOuterPorts();
		
		for(int i=7000;i<10000;i++) {
			if(!innerPorts.contains(i) && !outerPorts.contains(i)) {
				not_occupied_ports.add(Long.valueOf(i));
			}
		}
		return not_occupied_ports;
	}
}
