package com.jorge.twitter.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jorge.twitter.model.ResponseException;

@ControllerAdvice
public class RestExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DuplicateUserException.class)
  protected ResponseEntity<Object> handleDuplicateUsser(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(UserDoesNotExistException.class)
  protected ResponseEntity<Object> handleUserNotFound(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(AlreadyFollowingException.class)
  protected ResponseEntity<Object> handleUserAlreadyFollowed(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(NotFollowingException.class)
  protected ResponseEntity<Object> handleUserNotFollowed(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(SelfFollowException.class)
  protected ResponseEntity<Object> handleSelfFollow(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(TokenUnauthorizedException.class)
  protected ResponseEntity<Object> handleAuthorizationException(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }

  @ExceptionHandler(MessageTooLargeException.class)
  protected ResponseEntity<Object> handleMessageTooLargeException(RuntimeException ex, WebRequest request) {
    ResponseException responseMessage = new ResponseException();
    responseMessage.setMessage(ex.getMessage());
    return handleExceptionInternal(ex, responseMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
