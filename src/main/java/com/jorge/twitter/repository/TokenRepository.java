package com.jorge.twitter.repository;

import com.jorge.twitter.model.Token;
import com.jorge.twitter.model.User;

public interface TokenRepository {

  Token addToken(User user);

  void deleteToken(String token);

  boolean isTokenAuthorized(String token, String username);
}
