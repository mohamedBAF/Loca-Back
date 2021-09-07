package com.onegateafrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.AdminOGA;

@Repository
public interface AdminOGARepository extends JpaRepository<AdminOGA, Long> {

}
