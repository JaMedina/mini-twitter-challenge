package com.jorge.twitter.repository.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.jorge.twitter.model.RootDomain;
import com.jorge.twitter.repository.RootRepository;

public abstract class RootRepositoryImpl <T extends RootDomain>  implements RootRepository<T> {

	@Autowired
	private DataSource dataSource;
	private NamedParameterJdbcTemplate jdbcTemplate;
	private Class<T> domainClass;
	
	@Override
	public T findById(Long id) {
		Objects.requireNonNull(id);
		String sql = String.format("SELECT * FROM %s WHERE id = :id", getTableName());
		return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), getRowMapper());
	}

	@Override
	public void delete(T value) {
		Objects.requireNonNull(value);
		String sql = String.format("DELETE FROM %s WHERE id = :id", getTableName());
		jdbcTemplate.update(sql, Collections.singletonMap("id", value.getId()));
	}

	@Override
	public List<T> findAll() {
		String sql = String.format("SELECT * FROM %s", getTableName());
		return jdbcTemplate.query(sql, getRowMapper());
	}

	protected NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	protected RowMapper<T> getRowMapper() {
		return new BeanPropertyRowMapper<>(domainClass);
	}

	protected void save(String sql, T value) {
		jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(value));
	}
	
	protected abstract Object getTableName();
	

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void initialize() {
		this.domainClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), RootRepositoryImpl.class);
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}
