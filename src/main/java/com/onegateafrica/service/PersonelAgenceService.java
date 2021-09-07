package com.onegateafrica.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Alerte;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Users;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.enums.Role;
import com.onegateafrica.repository.AgenceRepository;
import com.onegateafrica.repository.PersonelAgenceRepository;
import com.onegateafrica.repository.UsersRepository;

@Service
public class PersonelAgenceService {
  public final static String UPLOADDIRECTORY = "C:\\Users\\ACER\\Desktop\\dev_pfe\\Loca-Front\\src\\uploads";

  @Autowired
  PersonelAgenceRepository personelAgenceRepository;
  @Autowired
  UsersRepository usersRepository;

  @Autowired
  AgenceRepository agenceRepository;
  public ResponseEntity<PersonelAgence> agenceRegister(long id) {
    if (id == 0L) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> user = usersRepository.findById(id);
    if (user.isPresent()) {
      PersonelAgence personelAgence = new PersonelAgence();
      personelAgence.setEmail(user.get().getEmail());
      personelAgence.setPassword(user.get().getPassword());
//      oga1605
      personelAgence.setDateCreation(new Timestamp(System.currentTimeMillis()));
      personelAgence.setRole(Role.ADMIN);
      personelAgenceRepository.save(personelAgence);

      return ResponseEntity.ok(personelAgence);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  public ResponseEntity<PersonelAgence> agenceRegisterNumTel(String numTel) {
    if (numTel == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> user = usersRepository.findByEmail(numTel);
    if (user.isPresent()) {
      PersonelAgence personelAgence = new PersonelAgence();
      personelAgence.setEmail(user.get().getEmail());
      personelAgence.setPassword(user.get().getPassword());
      personelAgenceRepository.save(personelAgence);
      return ResponseEntity.ok(personelAgence);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public boolean existsEmail(String email) {
    return personelAgenceRepository.existsByEmail(email);
  }



  /****OGA-1404****/

  //create agence insc
  public ResponseEntity<PersonelAgence> addPersonnel(long id, String nom, String prenom, String cin, Agence agence) {

    Optional<PersonelAgence> personelAgence = personelAgenceRepository.findById(id);
    if (personelAgence.isPresent()) {
      personelAgence.get().setNom(nom);
      personelAgence.get().setPrenom(prenom);
      personelAgence.get().setCin(cin);
      personelAgence.get().setAgence(agence);
      personelAgenceRepository.save(personelAgence.get());
      return ResponseEntity.ok(personelAgence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /****end OGA-1404****/
  public ResponseEntity<PersonelAgence> retrieveEmail(String email) {
    if (email == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<PersonelAgence> personel = personelAgenceRepository.findByEmail(email);
    if (personel.isPresent()) {
      return ResponseEntity.ok(personel.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }



  public ResponseEntity<PersonelAgence>  addPersonelAgence(PersonelAgence personelAgence) {
    if (personelAgence == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    personelAgence.setDateCreation(new Timestamp(System.currentTimeMillis()));
     personelAgenceRepository.save(personelAgence);
    return ResponseEntity.ok(personelAgence);
  }







  //      oga1605
  public ResponseEntity<PersonelAgence> personelAgenceRegister(long id,PersonelAgence personelAgence) {

    Optional<Users> user = usersRepository.findById(id);

    if (user.isPresent()) {
      personelAgence.setEmail(user.get().getEmail());
      personelAgence.setPassword(user.get().getPassword());
      //      oga1605
      personelAgence.setDateCreation(new Timestamp(System.currentTimeMillis()));
      personelAgence.setRole(Role.PERSONNEL);
      personelAgenceRepository.save(personelAgence);
      return ResponseEntity.ok(personelAgence);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }


  public List<PersonelAgence> getAllPersonelAgenceByIdAgence(long id) {
    return  personelAgenceRepository.getAllPersonelAgenceByIdAgence(id);
  }

  public void deletePersonelAgence(long id) {
    Optional<PersonelAgence> personelAgence = personelAgenceRepository.findById(id);
    Optional<Users> user = usersRepository.findByEmail(personelAgence.get().getEmail());
    usersRepository.deleteById(user.get().getId());
    personelAgenceRepository.deleteById(id);
  }

  public ResponseEntity<PersonelAgence> getPersonelAgence(long id) {

    Optional<PersonelAgence> optionalPersonelAgence = personelAgenceRepository.findById(id);
    if (optionalPersonelAgence.isPresent()) {
      return ResponseEntity.ok(optionalPersonelAgence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<PersonelAgence> getPersonnelByEmail(String email) {
    if (email == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<PersonelAgence> personelAgence = personelAgenceRepository.findByEmail(email);
    if (personelAgence.isPresent()) {
      return ResponseEntity.ok(personelAgence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //update user profile
  public ResponseEntity<PersonelAgence> editProfilePersonnel(long id, PersonelAgence personelAgence) {
    if (personelAgence == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<PersonelAgence> optionalPersonelAgence = personelAgenceRepository.findById(id);
    if (optionalPersonelAgence.isPresent()) {
      personelAgence.setId(id);
      personelAgence.setPassword(optionalPersonelAgence.get().getPassword());
      personelAgence.setRole(optionalPersonelAgence.get().getRole());
      personelAgenceRepository.save(personelAgence);
      return ResponseEntity.ok(optionalPersonelAgence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<PersonelAgence> uploadImagePersonnel(long id, MultipartFile file) {
    if (file == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<PersonelAgence> personelAgence = personelAgenceRepository.findById(id);
    if (personelAgence.isPresent()) {
      String filename = personelAgence.get().getId() + file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(UPLOADDIRECTORY, filename);
      try {
        Files.write(fileNameAndPath, file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      personelAgence.get().setImgProfil(filename);
      personelAgenceRepository.save(personelAgence.get());
      return ResponseEntity.ok(personelAgence.get());

    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

    //  public List<PersonelAgence> retrieveAllPersonelQgences() {
//    List<PersonelAgence> PersonelAgences = (List<PersonelAgence>) personelAgenceRepository.findAll();
//
//    return PersonelAgences;
//  }
//
//
//  public PersonelAgence retrieveEmail(String email) {
//
//    return personelAgenceRepository.findByEmail(email);
//
//  }
//
//  //ajoutt
//

//
//
//
//  public PersonelAgence updatePersonelAgence(PersonelAgence p) {
//    return personelAgenceRepository.save(p);
//  }
//
//
//  public PersonelAgence retrievePersonelAgence(String id) {
//
//    PersonelAgence p = personelAgenceRepository.findById(Long.parseLong(id)).orElse(null);
//
//    return p;
//  }
//
//
//  public void deletePersonelAgence(long id) {
//    personelAgenceRepository.deleteById(id);
//  }

}


