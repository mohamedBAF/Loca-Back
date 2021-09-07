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
public class Utilisateur {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nom;
  private String prenom;
  private String email;
  private long numTel;
  private String password;
  private Boolean statusVerif;
  private Boolean statusBan;
  private int numberBan;
  private Boolean permaBan;
  private Timestamp dateBan;
  private Timestamp dateUnBan;
  private String ville;
  private int codePostal;
  private String description;
  private String urlSocialMedia;
  private String cin;
  private String imgProfil;
  private String imgPermis;
  private String codePermis;
  private Timestamp dateCreation;
  private String token;

  @ManyToOne()
  private AdminOGA adminOGA;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilisateur")
  private List<Vehicule> vehicules;

}
