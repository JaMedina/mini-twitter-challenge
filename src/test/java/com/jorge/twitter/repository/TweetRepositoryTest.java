package com.jorge.twitter.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jorge.twitter.model.Tweet;
import com.jorge.twitter.model.User;

public class TweetRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private TweetRepository  tweetRepository;

  @Autowired
  private UserRepository   userRepository;

  @Autowired
  private FollowRepository followRepository;

  private User             user1;
  private User             user2;
  private User             user3;

  @Before
  public void setup() {
    user1 = new User();
    user1.setUsername("test.user.tweets_1");
    user1.setName("test user tweets #1");
    user1 = userRepository.create(user1);
    user2 = new User();
    user2.setUsername("test.user.tweets_2");
    user2.setName("test user tweets #2");
    user2 = userRepository.create(user2);
    user3 = new User();
    user3.setUsername("test.user.tweets_3");
    user3.setName("test user tweets #3");
    user3 = userRepository.create(user3);
  }

  @Test
  public void testfindTweetsForUserWithoutSearchCriteria() {

    String tweetContents = "Heeelloooo!";
    tweetRepository.create(tweetContents, user1);
    sleep(3);
    tweetRepository.create(tweetContents, user2);
    sleep(3);
    tweetRepository.create(tweetContents, user3);

    List<Tweet> tweetsWithoutFollowing = tweetRepository.findTweetsForUser(user1);
    Assert.assertEquals(1, tweetsWithoutFollowing.size());
    Assert.assertEquals(tweetContents, tweetsWithoutFollowing.get(0).getMessage());
    Assert.assertEquals(user1.getId(), tweetsWithoutFollowing.get(0).getAuthorId());

    followRepository.follow(user1, user2);
    followRepository.follow(user2, user3);

    List<Tweet> tweetsWithFollowing = tweetRepository.findTweetsForUser(user1);
    Assert.assertEquals(2, tweetsWithFollowing.size());
    Assert.assertEquals(tweetContents, tweetsWithFollowing.get(0).getMessage());
    Assert.assertEquals(user2.getId(), tweetsWithFollowing.get(0).getAuthorId());

    Assert.assertEquals(tweetContents, tweetsWithFollowing.get(1).getMessage());
    Assert.assertEquals(user1.getId(), tweetsWithFollowing.get(1).getAuthorId());
    Assert.assertTrue(tweetsWithFollowing.get(0).getCreationDate().after(tweetsWithFollowing.get(1).getCreationDate()));

  }

  @Test
  public void testfindTweetsForUserWitSearchCriteria() {

    String helloEnglish = "Hello!";
    String helloSwedish = "hej!";
    String helloEnglishAndSwedish = "Hello == hej!";
    tweetRepository.create(helloEnglish, user1);
    sleep(3);
    tweetRepository.create(helloEnglishAndSwedish, user2);
    sleep(3);
    tweetRepository.create(helloSwedish, user1);

    followRepository.follow(user1, user2);

    List<Tweet> tweetsWithHelloEnglish = tweetRepository.findTweetsForUser(user1, "hello");
    Assert.assertEquals(2, tweetsWithHelloEnglish.size());
    Assert.assertEquals(helloEnglishAndSwedish, tweetsWithHelloEnglish.get(0).getMessage());
    Assert.assertEquals(user2.getId(), tweetsWithHelloEnglish.get(0).getAuthorId());
    Assert.assertEquals(helloEnglish, tweetsWithHelloEnglish.get(1).getMessage());
    Assert.assertEquals(user1.getId(), tweetsWithHelloEnglish.get(1).getAuthorId());

    List<Tweet> tweetsWithHelloSwedish = tweetRepository.findTweetsForUser(user1, "hej");
    Assert.assertEquals(2, tweetsWithHelloSwedish.size());
    Assert.assertEquals(helloSwedish, tweetsWithHelloSwedish.get(0).getMessage());
    Assert.assertEquals(user1.getId(), tweetsWithHelloSwedish.get(0).getAuthorId());
    Assert.assertEquals(helloEnglishAndSwedish, tweetsWithHelloSwedish.get(1).getMessage());
    Assert.assertEquals(user2.getId(), tweetsWithHelloSwedish.get(1).getAuthorId());

  }
}
