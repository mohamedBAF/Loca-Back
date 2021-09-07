package com.onegateafrica.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
public class Opt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String libelle;
  @Column(length = 5000)
  private String pathSVG;

  private Timestamp dateCreation;
  private Timestamp dateUpdate;

  @ManyToOne()
  private AdminOGA adminOGA;

  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "opt")
  private List<Vehicule> vehicules;

}
