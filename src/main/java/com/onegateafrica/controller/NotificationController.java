package com.onegateafrica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onegateafrica.dto.CategorieDTO;
import com.onegateafrica.dto.NotificationDTO;
import com.onegateafrica.dto.UtilisateurDTO;
import com.onegateafrica.dto.VehiculeDTO;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Notification;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.service.NotificationService;
import com.onegateafrica.service.OptionService;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/oauth")

@RestController
public class NotificationController {


  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private NotificationService notificationService;

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";


  public NotificationController(NotificationService notificationService) {
    super();
    this.notificationService = notificationService;
  }


  @PostMapping("/add-notification")
  @ResponseBody
  public ResponseEntity<Object> addnotification(@RequestBody NotificationDTO notificationDTO) {

    Notification notificationReq = modelMapper.map(notificationDTO, Notification.class);
    ResponseEntity<Notification> notif= notificationService.addNotification(notificationReq);
    if (notif.getStatusCodeValue() == 200) {
      NotificationDTO notifDTO = modelMapper.map(notif.getBody(), NotificationDTO.class);

      return new ResponseEntity<>(notifDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }

  @PostMapping("/accepterDemande")
  @ResponseBody
  public ResponseEntity<Object> AccepterDemande(@RequestBody NotificationDTO notificationDTO) {

    Notification notificationReq = modelMapper.map(notificationDTO, Notification.class);
    ResponseEntity<Notification> notif= notificationService.AccepterDemande(notificationReq);
    if (notif.getStatusCodeValue() == 200) {
      NotificationDTO notifDTO = modelMapper.map(notif.getBody(), NotificationDTO.class);

      return new ResponseEntity<>(notifDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }

  @PostMapping("/refuserDemande")
  @ResponseBody
  public ResponseEntity<Object> refuserDemande(@RequestBody NotificationDTO notificationDTO) {

    Notification notificationReq = modelMapper.map(notificationDTO, Notification.class);
    ResponseEntity<Notification> notif= notificationService.refuserDemande(notificationReq);
    if (notif.getStatusCodeValue() == 200) {
      NotificationDTO notifDTO = modelMapper.map(notif.getBody(), NotificationDTO.class);

      return new ResponseEntity<>(notifDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }

  @GetMapping("/getNotificationsbyreceveur/{id}")
  public List<NotificationDTO> getNotificationsbyreceveur(@PathVariable("id") long id) {
    return notificationService.getNotificationsbyreceveur(id).stream().map(notification -> modelMapper.map(notification, NotificationDTO.class))
        .collect(Collectors.toList());
  }

  @GetMapping("/getNotificationsbyreceveurAgence/{id}")
  public List<NotificationDTO> getNotificationsbyreceveurAgence(@PathVariable("id") long id) {
    return notificationService.getNotificationsbyreceveurAgence(id).stream().map(notification -> modelMapper.map(notification, NotificationDTO.class))
        .collect(Collectors.toList());
  }


  @DeleteMapping("/remove-notification/{notification-id}")
  @ResponseBody
  public void deleteNotif(@PathVariable("notification-id") long notifId) {

    notificationService.deleteNotif(notifId);
  }



  @GetMapping(value = "/getNotificationbyDemandeurIdAndVehiculeId/{id1}/{id}")
  public ResponseEntity<Object> getNotificationbyDemandeurIdAndVehiculeId(@PathVariable("id1") long id1,@PathVariable("id") long id) {
    ResponseEntity<Notification> notification = notificationService.getNotificationbyDemandeurIdAndVehiculeId(id1,id);
    if (notification.getStatusCodeValue() == 200) {
      NotificationDTO notificationDTO = modelMapper.map(notification.getBody(), NotificationDTO.class);
      return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    } else if(notification.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);



    }
  }


  @GetMapping(value = "/findByReceveurIdAndCriterAccptation/{id1}/{critere}")
  public ResponseEntity<Object> findByReceveurIdAndCriterAccptation(@PathVariable("id1") long id1,@PathVariable("critere") String critere) {
    ResponseEntity<Notification> notification = notificationService.findByReceveurIdAndCriterAccptation(id1,critere);
    if (notification.getStatusCodeValue() == 200) {
      NotificationDTO notificationDTO = modelMapper.map(notification.getBody(), NotificationDTO.class);
      return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    } else if(notification.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);



    }
  }



  @PutMapping("/modify-notification/{notification-id}")
  @ResponseBody
  public ResponseEntity<Object> modifyNotification(@PathVariable("notification-id") long notificationId, @RequestBody NotificationDTO notificationDTO) {

    Notification notificationReq = modelMapper.map(notificationDTO, Notification.class);
    ResponseEntity<Notification> notification = notificationService.updateCategorie(notificationId, notificationReq);

    if (notification.getStatusCodeValue() == 200) {
      NotificationDTO notificationRes = modelMapper.map(notification.getBody(), NotificationDTO.class);
      return new ResponseEntity<>(notificationRes, HttpStatus.OK);
    } else if (notification.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(notification.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }

}