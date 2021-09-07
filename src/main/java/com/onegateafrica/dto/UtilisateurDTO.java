package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.AdminOGA;

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
public class UtilisateurDTO implements Serializable {

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
  private transient AdminOGA adminOGA;

}
