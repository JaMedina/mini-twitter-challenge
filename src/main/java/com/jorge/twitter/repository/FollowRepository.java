package com.jorge.twitter.repository;

import java.util.List;

import com.jorge.twitter.model.Follow;
import com.jorge.twitter.model.User;

public interface FollowRepository extends RootRepository<Follow> {

  void follow(User user, User followee);

  void unfollow(User user, User followee);

  List<User> getFollowees(User user);

  List<User> getFollowers(User user);
}
