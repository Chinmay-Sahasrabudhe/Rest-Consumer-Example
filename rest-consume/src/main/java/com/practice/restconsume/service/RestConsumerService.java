package com.practice.restconsume.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.restconsume.Exception.InternalServerError;
import com.practice.restconsume.Exception.InvalidProviderException;
import com.practice.restconsume.Exception.ServiceTimedOutException;
import com.practice.restconsume.bean.ApiResponseBean;
import com.practice.restconsume.bean.ExceptionBean;

@Service
public class RestConsumerService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${pivotal.url}")
	private String pwcUrl;
	
	@Value("${bluemix.url}")
	private String blueUrl;
	
	@SuppressWarnings("finally")
	public List<ApiResponseBean> getDataFromApi() {
		
		List<ApiResponseBean> listApiResponse = new ArrayList<ApiResponseBean>();
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> response = null;
		String responseJsonStr = null;
		
		try {
				//calling pwc url
				response = restTemplate.getForEntity(pwcUrl, String.class);
				ApiResponseBean bean = null;
				if(response.getStatusCode() == HttpStatus.OK) {
					responseJsonStr = response.getBody().toString();
					bean = mapper.readValue(responseJsonStr, ApiResponseBean.class);
					listApiResponse.add(bean);
				} else {
					throw new InternalServerError("Internal Server Error");
				}
				
				//calling Bluemix url
				response = restTemplate.getForEntity(blueUrl, String.class);
				ApiResponseBean bean1 = null;
				if(response.getStatusCode() == HttpStatus.OK) {
					responseJsonStr = response.getBody().toString();
					bean1 = mapper.readValue(responseJsonStr, ApiResponseBean.class);
					listApiResponse.add(bean1);
				} else {
					throw new InternalServerError("Internal Server Error");
				}
			return listApiResponse;
		} catch(Exception ex) {
			
			if(ex instanceof ResourceAccessException && ex.getCause() instanceof SocketTimeoutException) {
				throw new ServiceTimedOutException("service timed out.");
			} else {
				throw new InternalServerError("Internal Server Error");
			}
		}
	}
	
	
	public ApiResponseBean getDataByProvider(String provider) {
		
		String url = null;
		if("pwc".equalsIgnoreCase(provider) ) {
			url = pwcUrl;
		} else if("blu".equalsIgnoreCase(provider)) {
			url = blueUrl;
		} else {
			throw new InvalidProviderException("Invalid Provider Entered");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> response = null;
		String responseJsonStr = null;
		ApiResponseBean bean = null;
		
		try {
				response = restTemplate.getForEntity(url, String.class);
				if(response.getStatusCode() == HttpStatus.OK) {
					responseJsonStr = response.getBody().toString();
					bean = mapper.readValue(responseJsonStr, ApiResponseBean.class);
				} else {
					throw new InternalServerError("Internal Server Error");
				}
				return bean;
			} catch(Exception ex) {
				if(ex instanceof ResourceAccessException && ex.getCause() instanceof SocketTimeoutException) {
					throw new ServiceTimedOutException("service timed out.");
				} else {
					throw new InternalServerError("Internal Server Error");
				}
			}
		
	}
	
}
