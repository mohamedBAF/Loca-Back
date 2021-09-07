package com.onegateafrica.entity;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onegateafrica.enums.Message;

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

public class Alerte {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String recipient;
  private Message message;
  private boolean View;
  private Timestamp createdAt;
  @ManyToOne()
  private AdminOGA adminOGA;
  @ManyToOne()
  private Utilisateur utilisateur;
  @JsonBackReference
  @ManyToOne()
  private Agence agence;
}