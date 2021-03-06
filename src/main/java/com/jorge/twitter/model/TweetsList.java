package com.jorge.twitter.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tweets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TweetsList {

  @XmlElement(name = "tweet", type = Tweet.class)
  private List<Tweet> tweets;

  public TweetsList() {}

  public TweetsList(List<Tweet> tweets) {
    tweets = new ArrayList<>(tweets);
  }

  public List<Tweet> getTweets() {
    return tweets;
  }

  public void setTweets(List<Tweet> tweets) {
    this.tweets = tweets;
  }
}
