package com.jorge.twitter.exceptions;

public class SelfFollowException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public SelfFollowException() {
    super("You cannot follow yourself.");
  }

}
