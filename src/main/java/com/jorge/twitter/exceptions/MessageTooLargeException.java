package com.jorge.twitter.exceptions;

public class MessageTooLargeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public MessageTooLargeException() {
    super("Your tweet exceed the maximum number of allowed characters.");
  }
}
