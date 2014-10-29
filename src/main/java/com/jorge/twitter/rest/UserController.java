package com.jorge.twitter.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jorge.twitter.model.User;
import com.jorge.twitter.model.UsersList;
import com.jorge.twitter.repository.TokenRepository;
import com.jorge.twitter.repository.UserRepository;

@RestController
@RequestMapping(value = "/rest", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

  @Autowired
  private UserRepository  userRepository;

  @Autowired
  private TokenRepository tokenRepository;

  private final Logger    log = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public ResponseEntity<UsersList> getAll() {
    log.debug("REST request to get all Users");
    List<User> users = userRepository.findAll();
    UsersList usersList = new UsersList();
    usersList.setUsers(users);
    return new ResponseEntity<>(usersList, HttpStatus.OK);
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  public ResponseEntity<User> get(@PathVariable Long id) {
    log.debug("REST request to get User : {}", id);
    User user = userRepository.findById(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public ResponseEntity<User> get(@RequestParam(value = "username") String username) {
    log.debug("REST request to get User : {}", username);
    User user = userRepository.findByUsername(username);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id) {
    log.debug("REST request to delete User : {}", id);
    User user = userRepository.findById(id);
    userRepository.delete(user);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(method = RequestMethod.POST, value = "/users/logout")
  public void logout(@RequestParam String token) {
    tokenRepository.deleteToken(token);
  }
}
