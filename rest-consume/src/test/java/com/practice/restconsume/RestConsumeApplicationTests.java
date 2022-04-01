package com.practice.restconsume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.restconsume.bean.ApiResponseBean;
import com.practice.restconsume.controller.RestConsumerController;
import com.practice.restconsume.service.RestConsumerService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@WebMvcTest(value = RestConsumerController.class)
class RestConsumeApplicationTests {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	MockMvc mockMvc;
	
		
	@MockBean
	RestConsumerService service;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@BeforeEach
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void testGetData() throws Exception {
		
		ApiResponseBean bean1 = new ApiResponseBean("2.153.0","Cloud Foundry sponsored by Pivotal");
		ApiResponseBean bean2 = new ApiResponseBean("2.172.0","IBM Bluemix");
		List<ApiResponseBean> listApiResponse = new ArrayList<ApiResponseBean>();
		listApiResponse.add(bean1);
		listApiResponse.add(bean2);
		
		Mockito.when(service.getDataFromApi()).thenReturn(listApiResponse);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cf")).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		verify(service).getDataFromApi();
		
		String responseValue = result.getResponse().getContentAsString(); 
		
		String compareJsonValue = mapper.writeValueAsString(listApiResponse);
		
		assertEquals(compareJsonValue, responseValue);
	}
	
	@Test
	public void testGetDataForProviderPWC() throws Exception {
		
		ApiResponseBean bean = new ApiResponseBean("2.153.0","Cloud Foundry sponsored by Pivotal");
				
		Mockito.when(service.getDataByProvider(any(String.class))).thenReturn(bean);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cf/{provider}", "pwc").accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		verify(service).getDataByProvider(any(String.class));
		
		String responseValue = result.getResponse().getContentAsString(); 
		
		String compareJsonValue = mapper.writeValueAsString(bean);
		
		assertEquals(compareJsonValue, responseValue);
	}
	
	@Test
	public void testGetDataForProviderBlue() throws Exception {
		
		ApiResponseBean bean = new ApiResponseBean("2.172.0","IBM Bluemix");
		
		Mockito.when(service.getDataByProvider(any(String.class))).thenReturn(bean);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cf/{provider}", "blu")).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		verify(service).getDataByProvider(any(String.class));
		
		String responseValue = result.getResponse().getContentAsString(); 
		
		String compareJsonValue = mapper.writeValueAsString(bean);
		
		assertEquals(compareJsonValue, responseValue);
	}
	
	@Test
	public void testGetDataForProviderNegative() throws Exception {
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cf/{provider}", "test")).andReturn();
		
		verify(service).getDataByProvider(any(String.class));
		String responseValue = result.getResponse().getContentAsString(); 
		
		assertEquals("", responseValue);
	}
	
}
