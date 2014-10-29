package com.jorge.twitter.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jorge.twitter.exceptions.SelfFollowException;
import com.jorge.twitter.model.User;
import com.jorge.twitter.model.UsersList;
import com.jorge.twitter.repository.FollowRepository;
import com.jorge.twitter.repository.UserRepository;


@RestController
@RequestMapping(value = "/rest", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class FollowController {

  private final Logger     log = LoggerFactory.getLogger(FollowController.class);

  @Autowired
  private FollowRepository followRepository;

  @Autowired
  private UserRepository   userRepository;

  @RequestMapping(value = "/follow", method = RequestMethod.POST)
  public ResponseEntity<User> follow(@RequestParam(value = "username") String username, @RequestParam(value = "followeeUsername") String followeeUsername) {
    log.debug("REST request to follow User : {}", followeeUsername);
    Assert.notNull(username);
    Assert.notNull(followeeUsername);
    if (username.equals(followeeUsername)) {
      throw new SelfFollowException();
    }
    User user = userRepository.findByUsername(username);
    User followee = userRepository.findByUsername(followeeUsername);
    followRepository.follow(user, followee);
    return new ResponseEntity<>(followee, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/follow", method = RequestMethod.DELETE)
  public ResponseEntity<User> unFollow(@RequestParam(value = "username") String username, @RequestParam(value = "followeeUsername") String followeeUsername) {
    log.debug("REST request to unfollow User : {}", followeeUsername);
    Assert.notNull(username);
    Assert.notNull(followeeUsername);
    User user = userRepository.findByUsername(username);
    User followee = userRepository.findByUsername(followeeUsername);
    followRepository.unfollow(user, followee);
    return new ResponseEntity<>(followee, HttpStatus.OK);
  }

  @RequestMapping(value = "/follow", method = RequestMethod.GET)
  public ResponseEntity<UsersList> getFollowers(@RequestParam(value = "username") String username) {
    log.debug("REST request to get all Followers");
    User user = userRepository.findByUsername(username);
    List<User> users = followRepository.getFollowers(user);
    UsersList usersList = new UsersList();
    usersList.setUsers(users);

    return new ResponseEntity<>(usersList, HttpStatus.OK);
  }

  @RequestMapping(value = "/followee", method = RequestMethod.GET)
  public ResponseEntity<UsersList> getFollowees(@RequestParam(value = "username") String username) {
    log.debug("REST request to get all Followees");
    User user = userRepository.findByUsername(username);
    List<User> users = followRepository.getFollowees(user);
    UsersList usersList = new UsersList();
    usersList.setUsers(users);
    return new ResponseEntity<>(usersList, HttpStatus.OK);
  }

}
