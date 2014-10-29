package com.jorge.twitter.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jorge.twitter.exceptions.AlreadyFollowingException;
import com.jorge.twitter.exceptions.NotFollowingException;
import com.jorge.twitter.model.User;

public class FollowRepositoryTest extends BaseRepositoryTest{

  @Autowired
  private FollowRepository followRepository;

  @Autowired
  private UserRepository   userRepository;

  private User             user1;
  private User             user2;
  private User             user3;

  @Before
  public void setup() {
    user1 = new User();
    user1.setUsername("test.follow_1");
    user1.setName("test follow #");
    user1 = userRepository.create(user1);
    user2 = new User();
    user2.setUsername("test.follow_2");
    user2.setName("test follow #2");
    user2 = userRepository.create(user2);
    user3 = new User();
    user3.setUsername("test.follow_3");
    user3.setName("test follow #");
    user3 = userRepository.create(user3);
  }

  @Test
  public void testfollowAlreadyFollowedUser() {

    followRepository.follow(user1, user2);
    try {
      followRepository.follow(user1, user2);
      Assert.fail("A user can not follow the same user twice!");
    } catch (AlreadyFollowingException e) {
      // everything ok!
    }
  }

  @Test
  public void testunfollowNotFolowedUser() {

    try {
      followRepository.unfollow(user1, user2);
      Assert.fail("A user can not stop following a user that is not followed");
    } catch (NotFollowingException e) {
      // everything ok!
    }

    followRepository.follow(user1, user2);
    try {
      followRepository.unfollow(user1, user3);
      Assert.fail("A user can not stop following a user that is not followed");
    } catch (NotFollowingException e) {
      // everything ok!
    }
  }

  @Test
  public void testFindWhoTheUserFollows() {
    followRepository.follow(user1, user2);
    followRepository.follow(user1, user3);
    List<User> users = followRepository.getFollowees(user1);
    Assert.assertNotNull(users);
    Assert.assertEquals(2, users.size());
    Assert.assertTrue(users.contains(user2));
    Assert.assertTrue(users.contains(user3));
  }

  @Test
  public void testFindFollowersForUser() {
    followRepository.follow(user1, user2);
    followRepository.follow(user3, user2);
    List<User> users = followRepository.getFollowers(user2);
    Assert.assertNotNull(users);
    Assert.assertEquals(2, users.size());
    Assert.assertTrue(users.contains(user1));
    Assert.assertTrue(users.contains(user3));
  }
}
