package com.onegateafrica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Notification;
import com.onegateafrica.entity.Utilisateur;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findByCriterAccptation(String name);
  List<Notification> findByReceveurId( long id);
  List<Notification> findByReceveurAgenceId( long id);
  Optional<Notification> findByDemandeurIdAndVehiculeId(long id1,long id);
  Optional<Notification> findByReceveurIdAndCriterAccptation(long id1,String critere);

}
