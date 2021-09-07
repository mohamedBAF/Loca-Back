package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.AdminOGA;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Promotion;

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
public class AgenceDTO implements Serializable {

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
  private transient List<PersonelAgence> personelsAgence;
  private transient AdminOGA adminOGA;
  private transient List<Promotion> promotions;
}
