package com.jorge.twitter.repository.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.jorge.twitter.model.Tweet;
import com.jorge.twitter.model.User;
import com.jorge.twitter.repository.TweetRepository;

@Repository
@Transactional
public class TweetRepositoryImpl extends RootRepositoryImpl<Tweet> implements TweetRepository {

  @Override
  public List<Tweet> findTweetsForUser(User user) {
    return findTweetsForUser(user, "");
  }

  @Override
  public List<Tweet> findTweetsForUser(User user, String search) {
    String query =
        "SELECT t.* " //
        + " FROM tweets t " //
        + " WHERE  " //
        + "       ( " //
        + "         t.author_id = :user_id " //
        + "        OR "//
        + "		    t.author_id IN (SELECT f.follow_id FROM follow f WHERE f.user_id = :user_id ) " //
        + "        ) " + " %s " + " ORDER BY t.creation_date DESC ";

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", user.getId());

    String predicate = "";
    if (!StringUtils.isEmpty(search)) {
      predicate = " AND LOWER( t.message ) LIKE :message ";
      parameters.put("message", "%" + search.toLowerCase() + "%");
    }
    query = String.format(query, predicate);
    List<Tweet> tweets = getJdbcTemplate().query(query, parameters, getRowMapper());
    return tweets;
  }

  @Override
  public void create(String message, User user) {
    Assert.hasText(message);
    Assert.notNull(user);
    Date creationDate = new Date();
    String query = //
        "INSERT INTO tweets (message,author_id,creation_date) "//
        + "VALUES ( :message, :author_id, :creation_date )";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("author_id", user.getId());
    parameters.put("message", message);
    parameters.put("creation_date", creationDate);
    getJdbcTemplate().update(query, parameters);
  }

  @Override
  protected Object getTableName() {
    return "tweets";
  }

}
