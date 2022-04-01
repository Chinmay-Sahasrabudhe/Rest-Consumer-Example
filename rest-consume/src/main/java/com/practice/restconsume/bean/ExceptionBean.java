package com.practice.restconsume.bean;

public class ExceptionBean {

	public ExceptionBean() {
	}
	
	private String error;
	private String description;
	public String getError() {
		return error;
	}
	public String getDescription() {
		return description;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
