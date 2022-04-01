package com.practice.restconsume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.practice.restconsume.bean.ApiResponseBean;
import com.practice.restconsume.service.RestConsumerService;

@RestController
public class RestConsumerController {
	
	@Autowired
	private RestConsumerService service;
	
	@GetMapping(path = "/cf", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getApiData() {
		
		List<ApiResponseBean> listResponse = null;
		
		listResponse = service.getDataFromApi();
			return new ResponseEntity<Object>(listResponse,HttpStatus.OK);  
	}
	
	@GetMapping(path = "/cf/{provider}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getDataByProvider(@PathVariable String provider) {
		ApiResponseBean apiRun = null;
		apiRun = service.getDataByProvider(provider);
			return new ResponseEntity<Object>(apiRun, HttpStatus.OK);
	}
}
