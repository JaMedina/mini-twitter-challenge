package com.jorge.twitter.repository;

import java.util.List;

import com.jorge.twitter.model.RootDomain;

public interface RootRepository<T extends RootDomain> {

	T findById(Long id);

	void delete(T value);

	List<T> findAll();
}