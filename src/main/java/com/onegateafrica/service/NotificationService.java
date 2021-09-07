package com.onegateafrica.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Notification;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.NotificationRepository;
import com.onegateafrica.repository.UsersRepository;
import com.onegateafrica.repository.VehiculeRepository;


@Service
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private VehiculeRepository vehiculeRepository;

  public ResponseEntity<Notification> addNotification(Notification notification) {
    if (notification == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    notification.setDateCreation(new Timestamp(System.currentTimeMillis()));
    notification.setDemande(true);
    notification.setAcceptation(false);
    notificationRepository.save(notification);
    return ResponseEntity.ok(notification);

  }




  public ResponseEntity<Notification> AccepterDemande(Notification notification) {
    if (notification == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    notification.setDateCreation(new Timestamp(System.currentTimeMillis()));
    notification.setCriterAccptation("accepter");
    notification.setAcceptation(true);
    notification.setDemande(false);
    notificationRepository.save(notification);
    Optional<Vehicule> vehicule = vehiculeRepository.findById(notification.getVehicule().getId());
    System.out.println(vehicule.get().getNbrVehicule());
    vehicule.get().setNbrVehicule(vehicule.get().getNbrVehicule()-1);
    System.out.println(vehicule.get().getNbrVehicule());
    if(vehicule.get().getNbrVehicule() == 0){

      vehicule.get().setDisponibilite(false);

    }

    vehiculeRepository.save(vehicule.get());


    return ResponseEntity.ok(notification);

  }

    public ResponseEntity<Notification> refuserDemande(Notification notification) {
      if (notification == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    notification.setDateCreation(new Timestamp(System.currentTimeMillis()));
    notification.setCriterAccptation("refuser");
    notification.setAcceptation(true);
    notification.setDemande(false);
    notificationRepository.save(notification);



    return ResponseEntity.ok(notification);

  }


  public List<Notification>  getNotificationsbyreceveur(long id){

return notificationRepository.findByReceveurId(id);

  }


  public List<Notification>  getNotificationsbyreceveurAgence(long id){

    return notificationRepository.findByReceveurAgenceId(id);

  }


  public ResponseEntity<Notification> getNotificationbyDemandeurIdAndVehiculeId(long id1,long id){

    Optional<Notification>  notification= notificationRepository.findByDemandeurIdAndVehiculeId(id1,id);
    if (notification.isPresent()) {
      return ResponseEntity.ok(notification.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  public ResponseEntity<Notification> findByReceveurIdAndCriterAccptation(long id1,String critere){

    Optional<Notification>  notification= notificationRepository.findByReceveurIdAndCriterAccptation(id1,critere);
    if (notification.isPresent()) {
      return ResponseEntity.ok(notification.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  public void deleteNotif(long id) {
    notificationRepository.deleteById(id);
  }


  public ResponseEntity<Notification> updateCategorie(long id, Notification notification) {


    if (notification == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Notification> optionalNotification = notificationRepository.findById(id);
    if (optionalNotification.isPresent()) {
      optionalNotification.get().setIfread(true);

      notificationRepository.save(optionalNotification.get());
      return ResponseEntity.ok(optionalNotification.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}

