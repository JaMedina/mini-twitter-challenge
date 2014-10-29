package com.jorge.twitter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jorge.twitter.exceptions.DuplicateUserException;
import com.jorge.twitter.model.Token;
import com.jorge.twitter.model.User;
import com.jorge.twitter.repository.TokenRepository;
import com.jorge.twitter.repository.UserRepository;

@RestController
@RequestMapping(value = "/public", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class PublicRestController {

  private final Logger    log = LoggerFactory.getLogger(PublicRestController.class);

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private UserRepository  userRepository;

  @RequestMapping(method = RequestMethod.POST, value = "/login")
  public ResponseEntity<Token> login(@RequestParam(value = "username") String username) {
    User user = userRepository.findByUsername(username);
    Token token = tokenRepository.addToken(user);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public ResponseEntity<User> create(@RequestParam(value = "username") String username, @RequestParam(value = "name") String name) {
    User user = new User();
    user.setName(name);
    user.setUsername(username);
    log.debug("REST request to create User : {}", user);
    if (userRepository.exists(user.getUsername())) {
      throw new DuplicateUserException(user.getUsername());
    }
    user = userRepository.create(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
}
