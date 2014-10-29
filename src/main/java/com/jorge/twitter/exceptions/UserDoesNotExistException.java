package com.jorge.twitter.exceptions;

public class UserDoesNotExistException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UserDoesNotExistException(String username) {
    super("The user " + username + " does not exist.");
  }
}
