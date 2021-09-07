package com.onegateafrica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Opt;

@Repository
public interface OptionRepository extends JpaRepository<Opt, Long> {

  @Override
  List<Opt> findAll();
}
