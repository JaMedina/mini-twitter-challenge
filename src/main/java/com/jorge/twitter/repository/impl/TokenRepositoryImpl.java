package com.jorge.twitter.repository.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.jorge.twitter.exceptions.TokenUnauthorizedException;
import com.jorge.twitter.model.Token;
import com.jorge.twitter.model.User;
import com.jorge.twitter.repository.TokenRepository;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
  private Map<String, Token> tokens = new ConcurrentHashMap<>();

  public Token addToken(User user) {
    Token token = isAlreadyLogedin(user);
    if (token == null) {
      String stringToken = UUID.randomUUID().toString();
      token = new Token();
      token.setToken(stringToken);
      token.setUser(user);
      tokens.put(stringToken, token);
      return token;
    }
    return token;
  }

  public void deleteToken(String token) {
    tokens.remove(token);
  }

  public boolean isTokenAuthorized(String stringToken, String username) {
    if (stringToken == null || username == null) {
      throw new TokenUnauthorizedException();
    }
    Token token = tokens.get(stringToken);
    if (token != null) {
      if (token.getUser().getUsername().equals(username)) {
        return true;
      }
    }
    return false;
  }

  private Token isAlreadyLogedin(User user) {
    for (String key : tokens.keySet()) {
      Token token = tokens.get(key);
      if (token.getUser().equals(user)) {
        return token;
      }
    }
    return null;
  }
}
