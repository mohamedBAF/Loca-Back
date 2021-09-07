package com.onegateafrica.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Vehicule;

@RepositoryRestResource
public interface VehiculeRepository extends JpaRepository<Vehicule, Long>, JpaSpecificationExecutor<Vehicule> {



  @Query("Select v from Vehicule v "
      + "where v.ville=:ville and "
      + "v.dateDebut>=:dateD and "
      + "v.prix<=:prixMax and "
      + "v.prix>=:prixMin and "
      + "v.dateFin<=:dateF"
  )
  public List<Vehicule> getVehiculeByDate(@Param("prixMin") float prixMin, @Param("prixMax")float prixMax ,@Param("ville")String ville ,@Param("dateD") Date dateDebut,@Param("dateF")  Date dateFin);


  @Query("Select v from Vehicule v "
      + "where v.utilisateur.id=:id"
  )
  public List<Vehicule> getAllVehiculeByIdUtilisateur(long id);




  /**1601**/
  @Query("Select v from Vehicule v "
      + "where v.agence.id=:id "

  )
  public List<Vehicule> getAllVehiculeByIdAgence(long id);//vehicule sans promotion


  @Query("Select v from Vehicule v "
      + "where v.agence.id=:id"
  )
  public List<Vehicule> getAllVehiculesByIdAgence(long id);
}

