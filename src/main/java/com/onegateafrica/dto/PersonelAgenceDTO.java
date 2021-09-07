package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Promotion;
import com.onegateafrica.enums.Role;

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
public class PersonelAgenceDTO implements Serializable {

  private long id;
  private String nom;
  private String prenom;
  private String cin;
  private String email;
  private long numTel;
  private String password;
  private Role role;
  private Timestamp dateCreation;
  private String imgProfil;
  private transient Agence agence;
  private transient List<Promotion> promotions;
}
