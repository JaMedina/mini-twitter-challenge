package com.jorge.twitter.exceptions;

import com.jorge.twitter.model.User;

public class NotFollowingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NotFollowingException(User user) {
    super("You are not following the User: " + user.getUsername());
  }
}
