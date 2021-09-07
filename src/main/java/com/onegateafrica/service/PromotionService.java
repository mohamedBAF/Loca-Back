package com.onegateafrica.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegateafrica.entity.Promotion;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.PromotionRepository;
import com.onegateafrica.repository.VehiculeRepository;

@Service
public class PromotionService {

  @Autowired
  private PromotionRepository promotionRepository;
  @Autowired
  private VehiculeRepository vehiculeRepository;

  //create promo
  public ResponseEntity<Promotion> addPromotion(Promotion promotion) {
    if (promotion == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    promotion.setDateCreation(new Timestamp(System.currentTimeMillis()));
    Optional<Vehicule> vehicule = vehiculeRepository.findById(promotion.getVehicule().getId());
    vehicule.get().setPromotion(promotion);
    promotionRepository.save(promotion);
    return ResponseEntity.ok(promotion);

  }

  //get all promo
  public List<Promotion> getPromotions() {
    return promotionRepository.findAll();
  }

  //get promo by id
  public ResponseEntity<Promotion> getPromotion(long id) {

    Optional<Promotion> optionalPromotion = promotionRepository.findById(id);
    if (optionalPromotion.isPresent()) {
      return ResponseEntity.ok(optionalPromotion.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //update promo
  public ResponseEntity<Promotion> updatePromotion(long id, Promotion promotion) {

    if (promotion == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Promotion> optionalPromotion = promotionRepository.findById(id);

    if (optionalPromotion.isPresent()) {
      promotion.setId(id);
      promotion.setDateCreation(promotion.getDateCreation());
      promotionRepository.save(promotion);
      return ResponseEntity.ok(optionalPromotion.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //delete promo
  public void deletePromotion(long id) {
    promotionRepository.deleteById(id);
  }


  //get all promo by id agence
  public List<Promotion> getAllPromotionByIdAgence(long id) {
    return promotionRepository.getAllPromotionByIdAgence(id);
  }

  //delete promo
  public void deletePromotionByVehiculeId(long id) {
    Optional<Promotion> optionalPromotion = promotionRepository.findPromotionByVehiculeId(id);
    if (optionalPromotion.isPresent()) {
      promotionRepository.deleteById(optionalPromotion.get().getId());
    }
  }
}
