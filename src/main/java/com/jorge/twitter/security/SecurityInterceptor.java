package com.jorge.twitter.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jorge.twitter.exceptions.TokenUnauthorizedException;
import com.jorge.twitter.repository.TokenRepository;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

  @Autowired
  private TokenRepository tokenRepository;

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse resposne, Object object, Exception exception) throws Exception {}

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse resposne, Object object, ModelAndView modelAndView) throws Exception {}

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse resposne, Object object) throws Exception {
    String token = request.getParameter("token");
    String username = request.getParameter("username");

    if (tokenRepository.isTokenAuthorized(token, username)) {
      return true;
    } else {
      throw new TokenUnauthorizedException();
    }
  }
}
