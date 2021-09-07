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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onegateafrica.enums.Role;

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
public class PersonelAgence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @JsonBackReference
  @ManyToOne()
  private Agence agence;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "personelAgence")
  private List<Promotion> promotions;

}
