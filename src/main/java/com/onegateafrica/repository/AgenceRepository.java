package com.onegateafrica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Utilisateur;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {

  @Override
  List<Agence> findAll();



  @Query("Select v.agence from Vehicule v "
      + "where v.id=:id"
  )
  public Optional<Agence> getAgenceByVehiculesId(@Param("id") long id);



}
