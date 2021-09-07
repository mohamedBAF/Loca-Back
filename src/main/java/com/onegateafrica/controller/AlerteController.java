package com.onegateafrica.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.onegateafrica.dto.AlerteDTO;
import com.onegateafrica.entity.Alerte;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.service.AdminOGAService;
import com.onegateafrica.service.AlerteService;
import com.onegateafrica.service.CategorieService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class AlerteController {

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";
  @Autowired
  AlerteService alerteService;

  @Autowired
  private ModelMapper modelMapper;

  public AlerteController(AlerteService alerteService) {
    super();
    this.alerteService = alerteService;
  }

  @PostMapping("/add-Alerte")
  @ResponseBody
  public ResponseEntity<Object> addAlertes(@RequestBody AlerteDTO alerteDTO) {

    Alerte alerteReq = modelMapper.map(alerteDTO, Alerte.class);
    ResponseEntity<Alerte> alerte = alerteService.addAlerte(alerteReq);
    if (alerte.getStatusCodeValue() == 200) {
      AlerteDTO alerteRes = modelMapper.map(alerte.getBody(), AlerteDTO.class);
      return new ResponseEntity<>(alerteRes, HttpStatus.OK);
    } else if (alerte.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
    }

  }


  @DeleteMapping("/remove-Alerte/{Alerte-id}")
  @ResponseBody
  public void deleteAlerte(@PathVariable("Alerte-id") long AlerteId) {

    alerteService.deleteAlerte(AlerteId);
  }

  @GetMapping("/retrieve-Alerte/{Alerte-id}")
  @ResponseBody
  public ResponseEntity<Object> retrieveAlerte(@PathVariable("Alerte-id") long AlerteId) {
    ResponseEntity<Alerte> alerte = alerteService.retrieveAlerte(AlerteId);
    if (alerte.getStatusCodeValue() == 200) {
      AlerteDTO alerteDto = modelMapper.map(alerte.getBody(), AlerteDTO.class);
      return new ResponseEntity<>(alerteDto, HttpStatus.OK);
    } else if(alerte.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }


  @GetMapping("/retrieve-all-Alertes")
  @ResponseBody
  public List<AlerteDTO> getAlertes() {
    return alerteService.retrieveAllAlertes().stream().map(alerte -> modelMapper.map(alerte, AlerteDTO.class))
        .collect(Collectors.toList());
  }


}