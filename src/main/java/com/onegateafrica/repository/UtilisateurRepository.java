package com.onegateafrica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

  boolean existsByEmail(String email);

  boolean existsById(Long id);

  public Optional<Utilisateur> findByEmail(String email);

  @Override
  List<Utilisateur> findAll();

  @Query("Select v.utilisateur from Vehicule v "
      + "where v.id=:id"
  )
  public Utilisateur getUtilisateurByVehicules(@Param("id") long id);



}
