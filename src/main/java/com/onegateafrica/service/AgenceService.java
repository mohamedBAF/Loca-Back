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
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.repository.AgenceRepository;
import com.onegateafrica.repository.PersonelAgenceRepository;

@Service
public class AgenceService {

  public final static String UPLOADDIRECTORY = System.getProperty("user.dir") + "/uploads";
  @Autowired
  AlerteService alerteService;
  @Autowired
  AgenceRepository agenceRepository;

  @Autowired
  PersonelAgenceRepository personelAgenceRepository;

  //OGA-1407

  public List<Agence> getAllAgences() {
    return agenceRepository.findAll();
  }

  public ResponseEntity<Agence> getAgence(long id) {

    Optional<Agence> agence = agenceRepository.findById(id);
    if (agence.isPresent()) {
      return ResponseEntity.ok(agence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //end OGA-1407


  //OGA-1406
  public ResponseEntity<Agence> banAgence(long id) {

    Optional<Agence> agence = agenceRepository.findById(id);

    if (agence.isPresent()) {
      agence.get().setStatusBan(true);
      agence.get().setNumberBan(agence.get().getNumberBan() + 1);
      if (agence.get().getNumberBan() == 1) {
        agence.get().setDateBan(new Timestamp(System.currentTimeMillis()));
        agence.get().setDateUnBan(new Timestamp(System.currentTimeMillis() + (864000 * 1000)));//ban 10days
        //            agence.setDateUnBan(new Timestamp(System.currentTimeMillis()+10000)); //10s testing

      }
      if (agence.get().getNumberBan() == 2) {
        agence.get().setDateBan(new Timestamp(System.currentTimeMillis()));
        agence.get().setDateUnBan(new Timestamp(System.currentTimeMillis() + 2592000000L));//ban 30days 2592000*1000
        //            agence.setDateUnBan(new Timestamp(System.currentTimeMillis()+10000)); //10s testing

      }
      if (agence.get().getNumberBan() == 3) {
        agence.get().setPermaBan(true);
      }
      agenceRepository.save(agence.get());
      return ResponseEntity.ok(agence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  public List<Agence> unBanAgencesAuto() {
    List<Agence> list = agenceRepository.findAll();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getDateUnBan() != null && !list.get(i).getPermaBan()) {
        long y = list.get(i).getDateUnBan().getTime();
        if (y < System.currentTimeMillis()) {
          list.get(i).setDateBan(null);
          list.get(i).setDateUnBan(null);
          list.get(i).setStatusBan(false);
        }
      }

    }
    return list;
  }

  public ResponseEntity<Agence> unBanAgenceManu(long id) {

    Optional<Agence> agence = agenceRepository.findById(id);
    if (agence.isPresent()) {
      agence.get().setDateBan(null);
      agence.get().setDateUnBan(null);
      agence.get().setStatusBan(false);
      agence.get().setPermaBan(false);
      agence.get().setNumberBan(agence.get().getNumberBan() - 1);

      agenceRepository.save(agence.get());
      return ResponseEntity.ok(agence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  //end OGA-1406


  /****OGA-1404****/

  //create agence insc
  public ResponseEntity<Agence> addAgence(long id, String nomAgence, String codePatente, MultipartFile file) {
    if (file == null || codePatente == null || nomAgence == null  ) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<PersonelAgence> personelAgence = personelAgenceRepository.findById(id);
    if (personelAgence.isPresent()) {
      Agence agence = new Agence();
      agence.setNomAgence(nomAgence);
      agence.setCodePatente(codePatente);
      agence.setPermaBan(false);
      agence.setStatusVerif(false);
      agence.setStatusBan(false);
      agence.setDateCreation(new Timestamp(System.currentTimeMillis()));


      String filename = codePatente + file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(UPLOADDIRECTORY, filename);
      try {
        Files.write(fileNameAndPath, file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      agence.setImgPatente(filename);
      agenceRepository.save(agence);
      Alerte alerte =new Alerte();
      alerte.setAgence(agence);
      alerteService.addAlerte(alerte);
      return ResponseEntity.ok(agence);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  /****end OGA-1404****/


  public Agence verifAgence(long p) {

    Agence u =  agenceRepository.findById(p).orElse(null);
    u.setStatusVerif(true);
    return agenceRepository.save(u);
  }



  public ResponseEntity<Agence> retrieveAgenceByVehicule(long id) {

    Optional<Agence> agence = agenceRepository.getAgenceByVehiculesId(id);
    if (agence.isPresent()) {
      return ResponseEntity.ok(agence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  public ResponseEntity<Agence> editProfileAgence(long id, Agence agence) {
    if (agence == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Agence> optionalAgence = agenceRepository.findById(id);
    if (optionalAgence.isPresent()) {
      agence.setId(id);
      agence.setDateCreation(optionalAgence.get().getDateCreation());
      agence.setDateBan(optionalAgence.get().getDateBan());
      agence.setDateUnBan(optionalAgence.get().getDateUnBan());
      agence.setImgProfil(optionalAgence.get().getImgProfil());
      agence.setImgPatente(optionalAgence.get().getImgPatente());
      agence.setNumberBan(optionalAgence.get().getNumberBan());
      agence.setPermaBan(optionalAgence.get().getPermaBan());
      agence.setStatusBan(optionalAgence.get().getStatusBan());
      agence.setStatusVerif(optionalAgence.get().getStatusVerif());
      agence.setAdminOGA(optionalAgence.get().getAdminOGA());

      agenceRepository.save(agence);
      return ResponseEntity.ok(optionalAgence.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
  }

  public ResponseEntity<Agence> uploadImageAgence(long id, MultipartFile file) {
    if (file == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Agence> agence = agenceRepository.findById(id);
    if (agence.isPresent()) {
      String filename = agence.get().getCodePatente() + file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(UPLOADDIRECTORY, filename);
      try {
        Files.write(fileNameAndPath, file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      agence.get().setImgProfil(filename);
      agenceRepository.save(agence.get());
      return ResponseEntity.ok(agence.get());

    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
