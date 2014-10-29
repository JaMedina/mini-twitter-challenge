package com.jorge.twitter.exceptions;

import com.jorge.twitter.model.User;

public class AlreadyFollowingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AlreadyFollowingException(User user) {
    super("You are already following the user: " + user.getUsername());
  }
}
