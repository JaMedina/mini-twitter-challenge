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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jorge.twitter.exceptions.MessageTooLargeException;
import com.jorge.twitter.model.Tweet;
import com.jorge.twitter.model.TweetsList;
import com.jorge.twitter.model.User;
import com.jorge.twitter.repository.TweetRepository;
import com.jorge.twitter.repository.UserRepository;

@RestController
@RequestMapping(value = "/rest", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class TweetController {

  private final Logger     log            = LoggerFactory.getLogger(TweetController.class);
  private static final int MAX_TWEET_SIZE = 140;

  @Autowired
  private TweetRepository  tweetRepository;
  @Autowired
  private UserRepository   userRepository;

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/tweets", method = RequestMethod.POST)
  public ResponseEntity<Tweet> create(@RequestParam(value = "authorId") Long authorId, @RequestParam(value = "message") String message) {
    if (message.length() > MAX_TWEET_SIZE) {
      throw new MessageTooLargeException();
    }
    Tweet tweet = new Tweet();
    tweet.setAuthorId(authorId);
    tweet.setMessage(message);

    log.debug("REST request to save Tweet : {}", tweet);
    Assert.hasText(tweet.getMessage());
    User user = userRepository.findById(tweet.getAuthorId());
    tweetRepository.create(tweet.getMessage(), user);
    return new ResponseEntity<>(tweet, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/tweets", method = RequestMethod.GET)
  public ResponseEntity<TweetsList> getAll(@RequestParam(value = "username") String username, @RequestParam(value = "filter", required = false) String filter) {
    log.debug("REST request to get all Tweets for User");
    Assert.notNull(username);
    User user = userRepository.findByUsername(username);

    List<Tweet> tweets = tweetRepository.findTweetsForUser(user, filter);
    TweetsList tweetsList = new TweetsList();
    tweetsList.setTweets(tweets);
    return new ResponseEntity<>(tweetsList, HttpStatus.OK);
  }
}
