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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onegateafrica.dto.PersonelAgenceDTO;
import com.onegateafrica.dto.UtilisateurDTO;
import com.onegateafrica.dto.VehiculeDTO;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.service.PersonelAgenceService;
import com.onegateafrica.service.UsersService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class PersonelAgenceController {

  @Autowired
  private PersonelAgenceService personelAgenceService;

  @Autowired
  private ModelMapper modelMapper;

  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";
  public final static String BAD_REQUEST = "BAD_REQUEST";

  public PersonelAgenceController(PersonelAgenceService personelAgenceService) {
    super();
    this.personelAgenceService = personelAgenceService;
  }

  @PostMapping("/choixRegisterAgence/{id}")
  public ResponseEntity<Object> choixRegisterAgence(@PathVariable long id) {
    ResponseEntity<PersonelAgence> personelAgence = personelAgenceService.agenceRegister(id);
    if (personelAgence.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelAgenceDTO = modelMapper.map(personelAgence.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelAgenceDTO, HttpStatus.CREATED);
    } else if(personelAgence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }

  @PostMapping("/choixRegisterAgenceNumTel/{numTel}")
  public ResponseEntity<Object> choixRegisterAgenceNumTel(@PathVariable String numTel) {
    ResponseEntity<PersonelAgence> personelAgence = personelAgenceService.agenceRegisterNumTel(numTel);
    if (personelAgence.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelAgenceDTO = modelMapper.map(personelAgence.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelAgenceDTO, HttpStatus.CREATED);
    } else if(personelAgence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }


  @GetMapping("/retrieve-personelByemail/{email}")
  @ResponseBody
  public ResponseEntity<Object> retrieveemail(@PathVariable("email") String email) {
    ResponseEntity<PersonelAgence> personel = personelAgenceService.retrieveEmail(email);
    if (personel.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelDTO = modelMapper.map(personel.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelDTO, HttpStatus.OK);
    } else if(personel.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }



  @PostMapping("/add-personel")
  @ResponseBody
  public ResponseEntity<Object> addPersonel(@RequestBody PersonelAgenceDTO personelDTO) {

    PersonelAgence personelReq = modelMapper.map(personelDTO, PersonelAgence.class);
    ResponseEntity<PersonelAgence> personel= personelAgenceService.addPersonelAgence(personelReq);
    if (personel.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelResDTO = modelMapper.map(personel.getBody(), PersonelAgenceDTO.class);

      return new ResponseEntity<>(personelResDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }
//oga1605
  @PostMapping("/personelAgenceRegister/{id}")
  @ResponseBody
  public ResponseEntity<Object> personelAgenceRegister(@PathVariable long id,@RequestBody PersonelAgenceDTO personelDTO) {

    PersonelAgence personelReq = modelMapper.map(personelDTO, PersonelAgence.class);
    ResponseEntity<PersonelAgence> personel= personelAgenceService.personelAgenceRegister(id,personelReq);
    if (personel.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelResDTO = modelMapper.map(personel.getBody(), PersonelAgenceDTO.class);

      return new ResponseEntity<>(personelResDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }

  @GetMapping(value = "/getAllPersonelAgenceByIdAgence/{id}")
  public List<PersonelAgenceDTO> getAllPersonelAgenceByIdAgence(@PathVariable("id") long id) {
    return personelAgenceService.getAllPersonelAgenceByIdAgence(id).stream().map(perso -> modelMapper.map(perso, PersonelAgenceDTO.class))
        .collect(Collectors.toList());
  }

  //delete option
  @DeleteMapping(value = "/deletePersonelAgence/{id}")
  public void deletePersonelAgence(@PathVariable("id") long id) {
    personelAgenceService.deletePersonelAgence(id);
  }


  @GetMapping(value = "/getPersonelAgence/{id}")
  public ResponseEntity<Object> getPersonelAgence(@PathVariable("id") long id) {
    ResponseEntity<PersonelAgence> personelAgence = personelAgenceService.getPersonelAgence(id);
    if (personelAgence.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelAgenceDTO = modelMapper.map(personelAgence.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelAgenceDTO, HttpStatus.OK);
    } else if(personelAgence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

      /**1504**/
    }
  }
  @GetMapping("/getPersonnelByEmail/{email}")
  @ResponseBody
  public ResponseEntity<Object> getPersonnelByEmail(@PathVariable("email") String email) {
    ResponseEntity<PersonelAgence> peronelAgence = personelAgenceService.getPersonnelByEmail(email);
    if (peronelAgence.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelAgenceDTO = modelMapper.map(peronelAgence.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelAgenceDTO, HttpStatus.OK);
    } else if (peronelAgence.getStatusCodeValue() == 404) {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NULL, HttpStatus.OK);

    }
  }
  //update user profile
  @PutMapping(value = "/editProfilePersonnel/{id}")
  public ResponseEntity<Object> updateProfile(@PathVariable("id") long id, @RequestBody PersonelAgenceDTO personelAgenceDTO) {
    PersonelAgence personelAgenceReq = modelMapper.map(personelAgenceDTO, PersonelAgence.class);
    ResponseEntity<PersonelAgence> personnelAgence = personelAgenceService.editProfilePersonnel(id, personelAgenceReq);

    if (personnelAgence.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelAgencRes = modelMapper.map(personnelAgence.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelAgencRes, HttpStatus.OK);
    } else if(personnelAgence.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(personnelAgence.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);
    }
  }

  @PutMapping("/uploadImagePersonnel/{id}")
  public ResponseEntity<Object> uploadImagePersonnel(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
    ResponseEntity<PersonelAgence> personeAgence = personelAgenceService.uploadImagePersonnel(id,file);
    if (personeAgence.getStatusCodeValue() == 200) {
      PersonelAgenceDTO personelAgenceDTO = modelMapper.map(personeAgence.getBody(), PersonelAgenceDTO.class);
      return new ResponseEntity<>(personelAgenceDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }

}
