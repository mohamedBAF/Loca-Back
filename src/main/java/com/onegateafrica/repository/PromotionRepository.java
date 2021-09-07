package com.onegateafrica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.onegateafrica.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
  @Override
  List<Promotion> findAll();

  /**1601**/
  @Query("Select p from Promotion p "
      + "where p.agence.id=:id"
  )
  public List<Promotion> getAllPromotionByIdAgence(long id);

  Optional<Promotion> findPromotionByVehiculeId(long id);
}
