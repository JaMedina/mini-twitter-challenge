package com.jorge.twitter.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jorge.twitter.exceptions.UserDoesNotExistException;
import com.jorge.twitter.model.User;
import com.jorge.twitter.repository.UserRepository;

@Repository
@Transactional
public class UserRepositoryImpl extends RootRepositoryImpl<User> implements UserRepository {

  @Override
  public User create(User user) {
    super.save("INSERT INTO users (username, name) VALUES (:username, :name) ", user);
    return findByUsername(user.getUsername());
  }

  @Override
  public User findByUsername(String username) {
    String query = "SELECT * FROM users WHERE username = :username";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("username", username);
    List<User> users = getJdbcTemplate().query(query, parameters, getRowMapper());

    if (users == null || users.isEmpty()) {
      throw new UserDoesNotExistException(username);
    }
    return users.get(0);
  }

  @Override
  public boolean exists(String username) {
    String query = "SELECT * FROM users WHERE username = :username";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("username", username);

    List<User> users = getJdbcTemplate().query(query, parameters, getRowMapper());

    return users != null && !users.isEmpty();
  }

  @Override
  protected Object getTableName() {
    return "users";
  }

}
