package com.onegateafrica.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne()
  private Agence receveurAgence;
  @ManyToOne()
  private Agence demandeurAgence;
  @ManyToOne()
  private Utilisateur receveur;
  @ManyToOne()
  private Utilisateur demandeur;
  @ManyToOne()
  private Vehicule vehicule;
  private boolean acceptation ;
  private boolean demande ;
  private String criterAccptation;
  private Timestamp dateCreation;
  private Date dateDebutDemande;
  private Date dateFinDemande;
private boolean ifread;
}