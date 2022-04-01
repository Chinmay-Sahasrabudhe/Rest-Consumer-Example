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
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/cf", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ApiResponseBean>> getApiData() {
		System.out.println("Inside getApiData");
		List<ApiResponseBean> listResponse = service.getDataFromApi();
		System.out.println("Inside getApiData END");
		return new ResponseEntity<List<ApiResponseBean>>(listResponse,HttpStatus.OK);  
	}
	
	/**
	 * 
	 * @param provider
	 * @return
	 */
	@GetMapping(path = "/cf/{provider}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseBean> getDataByProvider(@PathVariable String provider) {
		System.out.println("Inside getDataByProvider");
		ApiResponseBean apiRun  = service.getDataByProvider(provider);
		System.out.println("Inside getDataByProvider END");
		return new ResponseEntity<ApiResponseBean>(apiRun, HttpStatus.OK);
	}
}
