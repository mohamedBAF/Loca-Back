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

import com.onegateafrica.entity.Alerte;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.Users;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.UsersRepository;
import com.onegateafrica.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

  public final static String UPLOADDIRECTORY = "C:\\Users\\HP\\Desktop\\UI\\Loca-Front\\Loca-Front\\public\\uploads";
  @Autowired
  AlerteService alerteService;
  @Autowired
  UtilisateurRepository utilisateurRepository;
  @Autowired
  UsersRepository usersRepository;


  //create user
  public ResponseEntity<Utilisateur> addUser(long id, String nom, String prenom, String cin, String codePermis, MultipartFile file) {
    if (nom == null || prenom == null ||cin == null || codePermis == null || file == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Utilisateur> updateUtilisateur = utilisateurRepository.findById(id);
    if (updateUtilisateur.isPresent()) {

      updateUtilisateur.get().setNom(nom);
      updateUtilisateur.get().setPrenom(prenom);
      updateUtilisateur.get().setCin(cin);
      updateUtilisateur.get().setCodePermis(codePermis);
      updateUtilisateur.get().setPermaBan(false);
      updateUtilisateur.get().setStatusVerif(false);
      updateUtilisateur.get().setStatusBan(false);

      String filename = updateUtilisateur.get().getCin() + file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(UPLOADDIRECTORY, filename);

      try {
        Files.write(fileNameAndPath, file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      updateUtilisateur.get().setImgPermis(filename);
      utilisateurRepository.save(updateUtilisateur.get());
      Alerte alerte = new Alerte();
      alerte.setUtilisateur(updateUtilisateur.get());
      alerteService.addAlerte(alerte);
      return ResponseEntity.ok(updateUtilisateur.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }


  public ResponseEntity<Utilisateur> utilisateurRegister(Long id) {
    Optional<Users> user = usersRepository.findById(id);
    if (user.isPresent()) {
      Utilisateur utilisateur = new Utilisateur();
      utilisateur.setEmail(user.get().getEmail());
      utilisateur.setPassword(user.get().getPassword());
      utilisateurRepository.save(utilisateur);
      return ResponseEntity.ok(utilisateur);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<Utilisateur> utilisateurRegisterNumTel(String numTel) {
    if (numTel == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> user = usersRepository.findByEmail(numTel);
    if (user.isPresent()) {
      Utilisateur utilisateur = new Utilisateur();
      utilisateur.setEmail(user.get().getEmail());
      utilisateur.setPassword(user.get().getPassword());
      utilisateurRepository.save(utilisateur);
      return ResponseEntity.ok(utilisateur);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //OGA-1407
  public List<Utilisateur> getAllUtilisateurs() {
    return utilisateurRepository.findAll();
  }
  public ResponseEntity<Utilisateur> getUtilisateur(long id) {

    Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
    if (utilisateur.isPresent()) {
      return ResponseEntity.ok(utilisateur.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  //end OGA-1407

  //OGA-1406
  public ResponseEntity<Utilisateur> banUtilisateur(long id) {

    Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
    if (utilisateur.isPresent()) {
      utilisateur.get().setStatusBan(true);
      utilisateur.get().setNumberBan(utilisateur.get().getNumberBan() + 1);
      if (utilisateur.get().getNumberBan() == 1) {
        utilisateur.get().setDateBan(new Timestamp(System.currentTimeMillis()));
        // utilisateur.get().setDateUnBan(new Timestamp(System.currentTimeMillis() + (864000 * 1000)));//ban 10days
        utilisateur.get().setDateUnBan(new Timestamp(System.currentTimeMillis() + 10000)); //10s testing
      }
      if (utilisateur.get().getNumberBan() == 2) {
        utilisateur.get().setDateBan(new Timestamp(System.currentTimeMillis()));
        //utilisateur.get().setDateUnBan(new Timestamp(System.currentTimeMillis() + 2592000000L));//ban 30days 2592000*1000
        utilisateur.get().setDateUnBan(new Timestamp(System.currentTimeMillis() + 10000)); //10s testing
      }
      if (utilisateur.get().getNumberBan() == 3) {
        utilisateur.get().setPermaBan(true);
      }
      utilisateurRepository.save(utilisateur.get());
      return ResponseEntity.ok(utilisateur.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  public List<Utilisateur> unBanUtilisateursAuto() {

    List<Utilisateur> list = utilisateurRepository.findAll();

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

  public ResponseEntity<Utilisateur> unBanUtilisateurManu(long id) {

    Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
    if (utilisateur.isPresent()) {
      //    long x =utilisateur.getDateBan().getTime();
      utilisateur.get().setDateBan(null);
      utilisateur.get().setDateUnBan(null);
      utilisateur.get().setStatusBan(false);
      utilisateur.get().setPermaBan(false);
      utilisateur.get().setNumberBan(utilisateur.get().getNumberBan() - 1);
      utilisateurRepository.save(utilisateur.get());
      return ResponseEntity.ok(utilisateur.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  //end OGA-1406

  public ResponseEntity<Utilisateur> retrieveEmail(String email) {
    if (email == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
    if (utilisateur.isPresent()) {
      return ResponseEntity.ok(utilisateur.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //ajoutt
  public ResponseEntity<Utilisateur> addUtilisateur(Utilisateur utilisateur) {
    if (utilisateur == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    utilisateur.setDateCreation(new Timestamp(System.currentTimeMillis()));
    utilisateurRepository.save(utilisateur);
    return ResponseEntity.ok(utilisateur);
  }

  public Utilisateur verifUtilisateur(long p) {
    Utilisateur u = utilisateurRepository.findById(p).orElse(null);
    u.setStatusVerif(true);
    return utilisateurRepository.save(u);
  }

  //update user profile
  public ResponseEntity<Utilisateur> editProfile(long id, Utilisateur utilisateur) {
    if (utilisateur == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
    if (optionalUtilisateur.isPresent()) {
      utilisateur.setId(id);

      utilisateurRepository.save(utilisateur);
      return ResponseEntity.ok(optionalUtilisateur.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<Utilisateur> uploadImageUser(long id, MultipartFile file) {
    if (file == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
    if (utilisateur.isPresent()) {
      String filename = utilisateur.get().getCin() + file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(UPLOADDIRECTORY, filename);
      try {
        Files.write(fileNameAndPath, file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      utilisateur.get().setImgProfil(filename);
      utilisateurRepository.save(utilisateur.get());
      return ResponseEntity.ok(utilisateur.get());

    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


}
