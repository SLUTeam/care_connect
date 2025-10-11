package com.app.controlleradvice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.response.ApiError;
import com.app.response.Error;
import com.app.response.Response;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {

		Response response=new Response();
		response.setData("Validation Failed");
		Error err = new Error();
		err.setCode(HttpStatus.BAD_REQUEST.toString());
		err.setReason("Validation Failed");
		response.setError(err);
		List<String> errors = new ArrayList<>();
		BindingResult bindingResult = ex.getBindingResult();
		bindingResult.getAllErrors().forEach(error -> errors.add(error.getCode()));
		response.setErrorMessages(errors);
		response.setTimeStamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
