package com.practice.restconsume.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.practice.restconsume.bean.ExceptionBean;


@EnableWebMvc
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(value = {InternalServerError.class, ServiceTimedOutException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(RuntimeException ex) {
		ExceptionBean excBean = new ExceptionBean();
		excBean.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		excBean.setDescription(ex.getMessage());
		return new ResponseEntity<Object>(excBean, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*
	@ExceptionHandler(ServiceTimedOutException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleTimedOutException(final ServiceTimedOutException ex) {
		ExceptionBean excBean = new ExceptionBean();
		excBean.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		excBean.setDescription(ex.getMessage());
		return new ResponseEntity<Object>(excBean, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	
	@ExceptionHandler(value = {InvalidProviderException.class, IllegalStateException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoHandlerException(RuntimeException ex) {
		ExceptionBean excBean = new ExceptionBean();
		excBean.setError(HttpStatus.NOT_FOUND.toString());
		excBean.setDescription(ex.getMessage());
		return new ResponseEntity<Object>(excBean, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoContentException(final NoHandlerFoundException ex) {
		ExceptionBean excBean = new ExceptionBean();
		excBean.setError(HttpStatus.NOT_FOUND.toString());
		excBean.setDescription("Incorrect url mapping");
		return new ResponseEntity<Object>(excBean, HttpStatus.NOT_FOUND);
	}
	
}
