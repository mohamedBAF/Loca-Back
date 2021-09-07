package com.onegateafrica.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegateafrica.entity.Alerte;
import com.onegateafrica.repository.AlerteRepository;

@Service
public class AlerteService {

  @Autowired
  AlerteRepository alerteRepository;


  public List<Alerte> retrieveAllAlertes() {
    return alerteRepository.findAll();


  }

  //ajoutt


  public ResponseEntity<Alerte> addAlerte(Alerte alerte) {
    if (alerte==null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    alerte.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    alerteRepository.save(alerte);
    return ResponseEntity.ok(alerte);
  }



  public ResponseEntity<Alerte> retrieveAlerte(long id) {


    Optional<Alerte> alerte = alerteRepository.findById(id);
    if (alerte.isPresent()) {
      return ResponseEntity.ok(alerte.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  public void deleteAlerte(long id) {
    alerteRepository.deleteById(id);
  }

}