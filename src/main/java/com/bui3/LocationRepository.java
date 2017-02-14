package com.bui3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

	List<Location> findByParentIsNull();
}
