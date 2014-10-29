package com.jorge.twitter.exceptions;

public class TokenUnauthorizedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenUnauthorizedException() {
    super("You are not authorized to perform this action");
  }

}
