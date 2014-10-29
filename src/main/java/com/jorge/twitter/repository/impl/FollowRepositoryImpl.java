package com.jorge.twitter.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jorge.twitter.exceptions.AlreadyFollowingException;
import com.jorge.twitter.exceptions.NotFollowingException;
import com.jorge.twitter.model.Follow;
import com.jorge.twitter.model.User;
import com.jorge.twitter.repository.FollowRepository;

@Repository
@Transactional
public class FollowRepositoryImpl extends RootRepositoryImpl<Follow> implements FollowRepository {

  private RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

  @Override
  public void follow(User user, User followee) {
    Objects.requireNonNull(user);
    Objects.requireNonNull(followee);

    String query = "INSERT INTO follow (user_id,follow_id) "//
        + "VALUES ( :user_id, :follow_id )";
    Map<String, Object> parameters = new HashMap<>();;

    parameters.put("user_id", user.getId());
    parameters.put("follow_id", followee.getId());
    try {
      getJdbcTemplate().update(query, parameters);
    } catch (DuplicateKeyException e) {
      throw new AlreadyFollowingException(followee);
    }
  }

  @Override
  public void unfollow(User user, User followee) {
    Objects.requireNonNull(user);
    Objects.requireNonNull(followee);

    List<User> followedUsers = getFollowees(user);
    if (!followedUsers.contains(followee)) {
      throw new NotFollowingException(followee);
    }

    String query = "DELETE FROM follow "//
        + "WHERE user_id = :user_id " //
        + "AND follow_id = :follow_id ";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", user.getId());
    parameters.put("follow_id", followee.getId());
    getJdbcTemplate().update(query, parameters);
  }

  @Override
  public List<User> getFollowees(User user) {
    String query = "SELECT f.id, f.name, f.username " //
        + " FROM users u " //
        + "     INNER JOIN follow uf ON u.id = uf.user_id " //
        + "     INNER JOIN users f ON uf.follow_id = f.id " //
        + " WHERE u.id = :user_id";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", user.getId());

    List<User> followedUsers = getJdbcTemplate().query(query, parameters, userRowMapper);
    return followedUsers;
  }

  @Override
  public List<User> getFollowers(User user) {
    String query = "SELECT f.id, f.name, f.username " //
        + " FROM users u " //
        + "     INNER JOIN follow uf ON u.id = uf.follow_id " //
        + "     INNER JOIN users f ON uf.user_id = f.id " //
        + " WHERE u.id = :user_id";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", user.getId());
    List<User> followedUsers = getJdbcTemplate().query(query, parameters, userRowMapper);
    return followedUsers;
  }

  @Override
  protected Object getTableName() {
    return "follow";
  }

}
