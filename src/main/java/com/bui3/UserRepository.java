package com.bui3;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

	final String FIELDS_LIKE_QUERY =
			"select u from User u "
			+ "where u.username like %?1% or "
			+ "u.firstname like %?1% or "
			+ "u.lastname like %?1%";
	
	@Query(FIELDS_LIKE_QUERY)
	Page<User> findByStringFieldsLike(String field, Pageable pageable);
}
