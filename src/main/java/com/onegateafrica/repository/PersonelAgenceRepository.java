package com.onegateafrica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;


@Repository
public interface PersonelAgenceRepository extends JpaRepository<PersonelAgence, Long> {

  boolean existsByEmail(String email);

  boolean existsById(Long id);


  public Optional<PersonelAgence> findByEmail(String email);

  @Query("Select p from PersonelAgence p "
      + "where p.agence.id=:id"
  )
  public List<PersonelAgence> getAllPersonelAgenceByIdAgence(long id);
}
