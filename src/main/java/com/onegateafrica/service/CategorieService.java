package com.onegateafrica.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegateafrica.entity.Categorie;
import com.onegateafrica.repository.CategorieRepository;

@Service
public class CategorieService {


  @Autowired
  CategorieRepository categorieRepository;


  public List<Categorie> retrieveAllCategories() {
    List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();

    return categories;
  }

  //ajoutt

  public ResponseEntity<Categorie> addCategorie(Categorie categorie) {

    if (categorie == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    categorie.setDateCreation(new Timestamp(System.currentTimeMillis()));
    categorieRepository.save(categorie);
    return ResponseEntity.ok(categorie);
  }


  public ResponseEntity<Categorie> updateCategorie(long id, Categorie categorie) {


    if (categorie == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Categorie> optionalCategorie = categorieRepository.findById(id);
    if (optionalCategorie.isPresent()) {
      optionalCategorie.get().setLibelle(categorie.getLibelle());
      optionalCategorie.get().setAdminOGA(categorie.getAdminOGA());
      optionalCategorie.get().setDateCreation(new Timestamp(System.currentTimeMillis()));
      categorieRepository.save(optionalCategorie.get());
      return ResponseEntity.ok(optionalCategorie.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  public ResponseEntity<Categorie> retrieveCategorie(long id) {

    Optional<Categorie> categorie = categorieRepository.findById(id);
    if (categorie.isPresent()) {
      return ResponseEntity.ok(categorie.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  public void deleteCategorie(long id) {
    categorieRepository.deleteById(id);
  }

}
