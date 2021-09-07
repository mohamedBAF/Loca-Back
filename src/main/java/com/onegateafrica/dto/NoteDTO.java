package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;

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
public class NoteDTO implements Serializable {


  private Long id;


  private transient Utilisateur utilisateur;
  private  transient Vehicule vehicule;
  private String commentaire;
  private float rate;
  private Timestamp dateCreation;

}
