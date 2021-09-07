package com.onegateafrica.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Agence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nomAgence;
  private String adresse;
  private String ville;
  private int codePostal;
  private String urlSocialMedia;
  private Long numTel;
  private Boolean statusVerif;
  private Boolean statusBan;
  private int numberBan;
  private Boolean permaBan;
  private Timestamp dateBan;
  private Timestamp dateUnBan;
  private String imgProfil;
  private String imgPatente;
  private String codePatente;
  private String description;
  private Timestamp dateCreation;

  @JsonManagedReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
  private List<PersonelAgence> personelsAgence;

  @ManyToOne()
  private AdminOGA adminOGA;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
  private List<Vehicule> vehicules;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
  private List<Promotion> promotions;

}
