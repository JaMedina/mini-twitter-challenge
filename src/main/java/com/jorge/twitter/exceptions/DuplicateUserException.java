package com.jorge.twitter.exceptions;


public class DuplicateUserException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DuplicateUserException(String username) {
    super("The username " + username + " already exists. Please choose a different user.");
  }
}
