package com.practice.restconsume.Exception;

public class ServiceTimedOutException extends RuntimeException {

	public ServiceTimedOutException() {
	}

	public ServiceTimedOutException(String msg) {
		super(msg);
	}

}
