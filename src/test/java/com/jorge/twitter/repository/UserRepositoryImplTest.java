package com.jorge.twitter.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jorge.twitter.model.User;

public class UserRepositoryImplTest extends BaseRepositoryTest {
  @Autowired
  private UserRepository userRepository;

  @Test
  public void testfindByUsername() {
    User user = new User();
    user.setUsername("test.user.findByUsername");
    user.setName("test user findByUsername");
    user = userRepository.create(user);

    User foundUser = userRepository.findByUsername(user.getUsername());
    Assert.assertEquals(user.getUsername(), foundUser.getUsername());
    Assert.assertEquals(user.getName(), foundUser.getName());
    Assert.assertEquals(user.getId(), foundUser.getId());
  }
}
