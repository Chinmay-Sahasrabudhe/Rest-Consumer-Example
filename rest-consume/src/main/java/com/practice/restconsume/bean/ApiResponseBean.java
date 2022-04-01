package com.practice.restconsume.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseBean {
	
	public ApiResponseBean() {}
	
	public ApiResponseBean(String api_version, String description) {
		this.apiVersion = api_version;
		this.description = description;
	}
	
	@JsonProperty(value = "api_version")
	private String apiVersion;
	
	@JsonProperty(value = "description")
	private String description;

	public String getApiVersion() {
		return apiVersion;
	}

	public String getDescription() {
		return description;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
