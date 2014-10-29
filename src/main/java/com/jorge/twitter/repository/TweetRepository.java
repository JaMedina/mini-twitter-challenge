package com.jorge.twitter.repository;

import java.util.List;

import com.jorge.twitter.model.Tweet;
import com.jorge.twitter.model.User;

public interface TweetRepository extends RootRepository<Tweet> {

  List<Tweet> findTweetsForUser(User user);

  List<Tweet> findTweetsForUser(User user, String search);

  void create(String message, User user);
}
