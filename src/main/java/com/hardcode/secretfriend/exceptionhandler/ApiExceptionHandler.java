package com.hardcode.secretfriend.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hardcode.secretfriend.exception.UserNotAuthorizedException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException() {
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleExceptionInternal(ex,"Erro ao validar objeto, verificar validação dos campos", headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UserNotAuthorizedException.class)
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public void handleUserNotAuthorizedExceptio() {
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Erro> handleDataIntegrityViolationException() {
		return new ResponseEntity<ApiExceptionHandler.Erro>(new Erro("violação de chave 'unique'"),HttpStatus.BAD_REQUEST);
	}
	
	static class Erro{
		private String msg;

		public Erro(String msg) {
			this.msg = msg;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
	}
}
