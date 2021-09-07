package com.onegateafrica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {



}
