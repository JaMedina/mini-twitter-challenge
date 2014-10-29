package com.jorge.twitter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "token")
@XmlAccessorType(XmlAccessType.FIELD)
public class Token {
  private String token;
  private User   user;

  public Token() {}

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Token{token: " + token + "," + user + "}";
  }
}
