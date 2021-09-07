package com.onegateafrica.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.Notification;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.CategorieRepository;
import com.onegateafrica.repository.NotificationRepository;
import com.onegateafrica.repository.PersonelAgenceRepository;
import com.onegateafrica.repository.UtilisateurRepository;
import com.onegateafrica.repository.VehiculeRepository;

@Service
public class VehiculeService {

  @Autowired
  VehiculeRepository vehiculeRepository;

  @Autowired
  UtilisateurRepository utilisateurRepository;

  @Autowired
  PersonelAgenceRepository personelAgenceRepository;

  @Autowired
  NotificationRepository notificationRepository;

  @Autowired
  CategorieRepository categorieRepository;
  public final static String UPLOADDIRECTORY = "C:\\Users\\HP\\Desktop\\UI\\Loca-Front\\Loca-Front\\public\\uploads";


  public ResponseEntity<Vehicule> uploadImageVeh(long id, MultipartFile file) {
    if (file == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Vehicule> vehicule = vehiculeRepository.findById(id);
    if (vehicule.isPresent()) {
      String filename = vehicule.get().getUtilisateur().getCin() + file.getOriginalFilename();
      Path fileNameAndPath = Paths.get(UPLOADDIRECTORY, filename);
      try {
        Files.write(fileNameAndPath, file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      vehicule.get().setImage(filename);
      vehiculeRepository.save(vehicule.get());
      return ResponseEntity.ok(vehicule.get());

    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  public ResponseEntity<Vehicule> addVehiculeByUtilisateur(long id, Vehicule vehicule) {
    if (vehicule == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Utilisateur> utilisateurAdd = utilisateurRepository.findById(id);
    if (utilisateurAdd.isPresent()) {

      vehicule.setUtilisateur(utilisateurAdd.get());

      vehiculeRepository.save(vehicule);

      return ResponseEntity.ok(vehicule);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  //get all vehicules
  public List<Vehicule> getVehicules() {
    return vehiculeRepository.findAll();
  }


  public List<Vehicule> RechercheVehicule(float prixMin,float prixMax,String ville, Date dateDebut, Date dateFin) {


      return vehiculeRepository.getVehiculeByDate(prixMin, prixMax, ville, dateDebut, dateFin);
    }

    /***1500 => 1502***/



    public List<Vehicule> getAllVehiculeByIdUtilisateur(long id) {
      return  vehiculeRepository.getAllVehiculeByIdUtilisateur(id);
    }

    public void deleteVehicule(long id) {
      vehiculeRepository.deleteById(id);
    }

    public ResponseEntity<Vehicule> getVehicule(long id) {

      Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
      if (optionalVehicule.isPresent()) {
        return ResponseEntity.ok(optionalVehicule.get());
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    //update Option
    public ResponseEntity<Vehicule> updateVehicule(long id, Vehicule vehicule) {
      if (vehicule == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      Optional<Vehicule> optionalVehicule= vehiculeRepository.findById(id);
      if (optionalVehicule.isPresent()) {
        optionalVehicule.get().setMaison(vehicule.getMaison());
        optionalVehicule.get().setGamme(vehicule.getGamme());
        optionalVehicule.get().setAnnee(vehicule.getAnnee());
        optionalVehicule.get().setKmCompteur(vehicule.getKmCompteur());
        optionalVehicule.get().setNbrPlace(vehicule.getNbrPlace());
        optionalVehicule.get().setNbrCylindre(vehicule.getNbrCylindre());
        optionalVehicule.get().setPuissance(vehicule.getPuissance());
        optionalVehicule.get().setBoiteVitesse(vehicule.getBoiteVitesse());
        optionalVehicule.get().setEnergie(vehicule.getEnergie());
        optionalVehicule.get().setCategorie(vehicule.getCategorie());
        optionalVehicule.get().setDateDebut(vehicule.getDateDebut());
        optionalVehicule.get().setDateFin(vehicule.getDateFin());
        optionalVehicule.get().setDescription(vehicule.getDescription());
        optionalVehicule.get().setGouvernorat(vehicule.getGouvernorat());
        optionalVehicule.get().setVille(vehicule.getVille());
        optionalVehicule.get().setRue(vehicule.getRue());
        optionalVehicule.get().setRue(vehicule.getZip());
        optionalVehicule.get().setPrix(vehicule.getPrix());
        optionalVehicule.get().setNbrVehicule(vehicule.getNbrVehicule());
        vehiculeRepository.save(optionalVehicule.get());
        return ResponseEntity.ok(optionalVehicule.get());
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    }

  /***END 1500 => 1502****/


    public ResponseEntity<Vehicule> updateVehiculeDates(long id,Date dateDebutDemande,Date dateFinDemande, Vehicule vehicule) {
      if (vehicule == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      Optional<Vehicule> optionalVehicule= vehiculeRepository.findById(id);
      if (optionalVehicule.isPresent()) {
        optionalVehicule.get().setMaison(vehicule.getMaison());
        optionalVehicule.get().setGamme(vehicule.getGamme());
        optionalVehicule.get().setAnnee(vehicule.getAnnee());
        optionalVehicule.get().setKmCompteur(vehicule.getKmCompteur());
        optionalVehicule.get().setNbrPlace(vehicule.getNbrPlace());
        optionalVehicule.get().setNbrCylindre(vehicule.getNbrCylindre());
        optionalVehicule.get().setPuissance(vehicule.getPuissance());
        optionalVehicule.get().setBoiteVitesse(vehicule.getBoiteVitesse());
        optionalVehicule.get().setEnergie(vehicule.getEnergie());
        optionalVehicule.get().setCategorie(vehicule.getCategorie());
        optionalVehicule.get().setDateDebut(vehicule.getDateDebut());
        optionalVehicule.get().setDateFin(vehicule.getDateFin());
        optionalVehicule.get().setDescription(vehicule.getDescription());
        optionalVehicule.get().setGouvernorat(vehicule.getGouvernorat());
        optionalVehicule.get().setVille(vehicule.getVille());
        optionalVehicule.get().setRue(vehicule.getRue());
        optionalVehicule.get().setRue(vehicule.getZip());
        optionalVehicule.get().setPrix(vehicule.getPrix());
        optionalVehicule.get().setNbrVehicule(vehicule.getNbrVehicule());
        optionalVehicule.get().setDateDebutDemande(dateDebutDemande);
        optionalVehicule.get().setDateFinDemande(dateFinDemande);
        vehiculeRepository.save(optionalVehicule.get());
        return ResponseEntity.ok(optionalVehicule.get());
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    }



  public ResponseEntity<Vehicule> updateVehiculeViews(long id) {

    Optional<Vehicule> optionalVehicule= vehiculeRepository.findById(id);
    if (optionalVehicule.isPresent()) {

      optionalVehicule.get().setView(optionalVehicule.get().getView()+1);
      vehiculeRepository.save(optionalVehicule.get());
      return ResponseEntity.ok(optionalVehicule.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  public List<Vehicule> RefreshVehiculeDispo() {
    List<Vehicule> vehicules = new ArrayList<Vehicule>();
    List<Notification> notifications = notificationRepository.findByCriterAccptation("accepter");

    Date date = new Timestamp(System.currentTimeMillis());

    notifications.stream().forEach(e->
        { //System.out.println(e.getVehicule().getDateFinDemande().before(date));
          if(e.getDateFinDemande().before(date) ){
            e.getVehicule().setNbrVehicule(e.getVehicule().getNbrVehicule()+1);

            e.getVehicule().setDisponibilite(true);
            vehiculeRepository.save(e.getVehicule());
            notificationRepository.delete(e);

            vehicules.add(e.getVehicule());
          }
        }
    );
    return vehicules;
  }

  /***1601****/
  public List<Vehicule> getAllVehiculeByIdAgence(long id) {
    return vehiculeRepository.getAllVehiculeByIdAgence(id);
  }


  public ResponseEntity<Vehicule> addVehiculeByPersonnel(long id, Vehicule vehicule) {
    if (vehicule == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<PersonelAgence> personelAgence = personelAgenceRepository.findById(id);
    if (personelAgence.isPresent()) {

      vehicule.setAgence(personelAgence.get().getAgence());

      vehiculeRepository.save(vehicule);

      return ResponseEntity.ok(vehicule);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public List<Vehicule> getAllVehiculesByIdAgence(long id) {
    return vehiculeRepository.getAllVehiculesByIdAgence(id);
  }


  }
