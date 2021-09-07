package com.onegateafrica.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.Promotion;
import com.onegateafrica.entity.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculeDTO implements Serializable {

  private long id;

  private String gouvernorat;//wileya//E2
  private String rue;//wileya//E2
  private String zip;//wileya//E2
  private String longitude;
  private String latitude;


  private String ville;//mo3tamdia wla blasa bdhbt//E2
  private int annee;//3am ly tsan3t fih //E1
  private int nbrCylindre;// 4,3.../E1
  private String gamme;//E1
  private String maison;//E1
  private int puissance;//5,4 chevaux...//E1
  private float kmCompteur;//E1
  private String boiteVitesse;//E1
  private String energie;//essence ou mazout//E1
  private float prix;//E4
  private boolean disponibilite;
  private String image;//E3
  private int nbrPlace;//E1
  private Date dateDebut;//E2

  private Date dateFin;//E2
  private Date dateDebutDemande;
  private Date dateFinDemande;
  private int view;
  private int nbrVehicule; //3dad lkrahb m naw3ia adhika //E4
  private  String description;//E1
  //  private long categorieidd; //3dad lkrahb m naw3ia adhika //E4

  private transient Agence agence;

  private transient Utilisateur utilisateur;

  private transient Categorie categorie;

  private transient List<Opt> opt;

  private transient Promotion promotion;
}
