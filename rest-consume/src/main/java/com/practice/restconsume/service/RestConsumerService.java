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

@Service
public class RestConsumerService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${pivotal.url}")
	private String pwcUrl;
	
	@Value("${bluemix.url}")
	private String blueUrl;
	
	/**
	 * 
	 * @return List
	 */
	public List<ApiResponseBean> getDataFromApi() {
		System.out.println("Inside getDataFromApi service");
		List<ApiResponseBean> listApiResponse = new ArrayList<ApiResponseBean>();
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> response = null;
		String responseJsonStr = null;
		
		try {
				//calling pwc url
				System.out.println("getDataFromApi service - calling PWC api");
				response = restTemplate.getForEntity(pwcUrl, String.class);
				ApiResponseBean bean = null;
				System.out.println("getDataFromApi service - Status Code " +response.getStatusCode());
				if(response.getStatusCode() == HttpStatus.OK) {
					responseJsonStr = response.getBody().toString();
					bean = mapper.readValue(responseJsonStr, ApiResponseBean.class);
					listApiResponse.add(bean);
				} else {
					System.out.println("getDataFromApi service - Invalid response status code for PWC");
					throw new InternalServerError("Internal Server Error");
				}
				
				//calling Bluemix url
				System.out.println("getDataFromApi service - calling Bluemix api");
				response = restTemplate.getForEntity(blueUrl, String.class);
				ApiResponseBean bean1 = null;
				System.out.println("getDataFromApi service -Bluemix Status Code " +response.getStatusCode());
				if(response.getStatusCode() == HttpStatus.OK) {
					responseJsonStr = response.getBody().toString();
					bean1 = mapper.readValue(responseJsonStr, ApiResponseBean.class);
					listApiResponse.add(bean1);
				} else {
					System.out.println("getDataFromApi service - Invalid response status code for Bluemix");
					throw new InternalServerError("Internal Server Error");
				}
				System.out.println("getDataFromApi service END");
				return listApiResponse;
		} catch(Exception ex) {
			ex.printStackTrace(); //for local test
			if(ex instanceof ResourceAccessException && ex.getCause() instanceof SocketTimeoutException) {
				throw new ServiceTimedOutException("service timed out.");
			} else {
				throw new InternalServerError("Internal Server Error");
			}
		}
	}
	
	/**
	 * 
	 * @param provider
	 * @return ApiResponseBean
	 */
	public ApiResponseBean getDataByProvider(String provider) {
		System.out.println("Inside getDataByProvider for provider - " +provider);
		String url = null;
		if("pwc".equalsIgnoreCase(provider) ) {
			url = pwcUrl;
		} else if("blu".equalsIgnoreCase(provider)) {
			url = blueUrl;
		} else {
			System.out.println("getDataByProvider - Invalid provider input");
			throw new InvalidProviderException("Invalid Provider Entered");
		}
		System.out.println("getDataByProvider API - " + url);
		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> response = null;
		String responseJsonStr = null;
		ApiResponseBean bean = null;
		
		try {
				System.out.println("Calling api - "+ url);
				response = restTemplate.getForEntity(url, String.class);
				System.out.println("getDataByProvider - api response status code "+ response.getStatusCode());
				if(response.getStatusCode() == HttpStatus.OK) {
					responseJsonStr = response.getBody().toString();
					bean = mapper.readValue(responseJsonStr, ApiResponseBean.class);
				} else {
					System.out.println("getDataByProvider - api response status code is not 200 OK");
					throw new InternalServerError("Internal Server Error");
				}
				System.out.println("getDataByProvider END");
				return bean;
			} catch(Exception ex) {
				ex.printStackTrace(); //for local
				if(ex instanceof ResourceAccessException && ex.getCause() instanceof SocketTimeoutException) {
					throw new ServiceTimedOutException("service timed out.");
				} else {
					throw new InternalServerError("Internal Server Error");
				}
			}
		
	}
	
}
