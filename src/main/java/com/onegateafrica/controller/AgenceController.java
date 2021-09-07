package com.onegateafrica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onegateafrica.dto.AgenceDTO;
import com.onegateafrica.dto.PersonelAgenceDTO;
import com.onegateafrica.dto.OptionDTO;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.repository.AgenceRepository;
import com.onegateafrica.service.AgenceService;
import com.onegateafrica.service.AlerteService;
import com.onegateafrica.service.PersonelAgenceService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class AgenceController {

  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";

  public final static String BAD_REQUEST = "BAD_REQUEST";
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AgenceService agenceService;
  private AgenceRepository agenceRepository;

  public AgenceController(AgenceService agenceService) {
    super();
    this.agenceService = agenceService;
  }

  //OGA-1407
  @GetMapping("/getAllAgences")
  public List<AgenceDTO> getAllAgences() {
    return agenceService.getAllAgences().stream().map(agence -> modelMapper.map(agence, AgenceDTO.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/getAgence/{id}")
  public ResponseEntity<Object> getAgence(@PathVariable("id") long id) {
    ResponseEntity<Agence> agence = agenceService.getAgence(id);
    if (agence.getStatusCodeValue() == 200) {
      AgenceDTO agenceDTO = modelMapper.map(agence.getBody(), AgenceDTO.class);
      return new ResponseEntity<>(agenceDTO, HttpStatus.OK);
    } else if(agence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }


  //OGA-1406
  @PutMapping(value = "/banAgence/{id}")
  public ResponseEntity<Object> banAgence(@PathVariable("id") long id) {
    ResponseEntity<Agence> agence = agenceService.banAgence(id);
    if (agence.getStatusCodeValue() == 200) {
      AgenceDTO agenceDTO = modelMapper.map(agence.getBody(), AgenceDTO.class);
      return new ResponseEntity<>(agenceDTO, HttpStatus.OK);
    } else if(agence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }

  @PutMapping(value = "/unBanAgencesAuto")
  public List<AgenceDTO> unBanAgencesAuto() {
    return agenceService.unBanAgencesAuto().stream().map(agence -> modelMapper.map(agence, AgenceDTO.class))
        .collect(Collectors.toList());
  }

  @PutMapping(value = "/unBanAgenceManu/{id}")
  public ResponseEntity<Object> unBanAgenceManu(@PathVariable("id") long id) {
    ResponseEntity<Agence> agence = agenceService.unBanAgenceManu(id);
    if (agence.getStatusCodeValue() == 200) {
      AgenceDTO agenceDTO = modelMapper.map(agence.getBody(), AgenceDTO.class);
      return new ResponseEntity<>(agenceDTO, HttpStatus.OK);
    } else if(agence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }
  //END OGA-1406

  /****OGA-1404****/
  @Autowired
  private PersonelAgenceService personelAgenceService;


  @PutMapping("/addAgence/{id}")
  public ResponseEntity<Object> saveAgence(@PathVariable("id") long id, @RequestParam("nom") String nom, @RequestParam("prenom") String prenom,
      @RequestParam("cin") String cin, @RequestParam("nomAgence") String nomAgence, @RequestParam("codePatente") String codePatente,
      @RequestParam("imgPatente") MultipartFile file) {
    ResponseEntity<Agence> agence = agenceService.addAgence(id, nomAgence, codePatente, file);
    ResponseEntity<PersonelAgence> personelAgence = personelAgenceService.addPersonnel(id, nom, prenom, cin, agence.getBody());

    if (agence.getStatusCodeValue() == 200 && personelAgence.getStatusCodeValue() == 200) {
      return new ResponseEntity<>("Added", HttpStatus.OK);
    } else if(agence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }


  @PutMapping(value = "/verifier-agence/{id}")
  public Agence verifierAgence(@PathVariable("id") long id) {
    return agenceService.verifAgence(id);
  }

  @PutMapping(value = "/editProfileAgence/{id}")
  public ResponseEntity<Object> updateProfile(@PathVariable("id") long id, @RequestBody AgenceDTO agenceDTO) {
    Agence agenceReq = modelMapper.map(agenceDTO, Agence.class);
    ResponseEntity<Agence> agence = agenceService.editProfileAgence(id, agenceReq);

    if (agence.getStatusCodeValue() == 200) {
      AgenceDTO agenceRes = modelMapper.map(agence.getBody(), AgenceDTO.class);
      return new ResponseEntity<>(agenceRes, HttpStatus.OK);
    } else if(agence.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(agence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);
    }
  }

  @PutMapping("/uploadImageAgence/{id}")
  public ResponseEntity<Object> uploadImageAgence(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
    ResponseEntity<Agence> agence = agenceService.uploadImageAgence(id,file);
    if (agence.getStatusCodeValue() == 200) {
      AgenceDTO agenceDTO = modelMapper.map(agence.getBody(), AgenceDTO.class);
      return new ResponseEntity<>(agenceDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }



  @GetMapping(value = "/getAgenceByVehiculesContains/{id}")
  public ResponseEntity<Object> getOption(@PathVariable("id") long id) {
    ResponseEntity<Agence> agence = agenceService.retrieveAgenceByVehicule(id);
    if (agence.getStatusCodeValue() == 200) {
      AgenceDTO agenceDTO = modelMapper.map(agence.getBody(), AgenceDTO.class);
      return new ResponseEntity<>(agenceDTO, HttpStatus.OK);
    } else if(agence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }


}
