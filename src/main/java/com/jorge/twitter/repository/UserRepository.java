package com.jorge.twitter.repository;

import com.jorge.twitter.model.User;

public interface UserRepository extends RootRepository<User>{

	User create(User user);

	User findByUsername(String username);

	boolean exists(String username);
}
