package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.AdminOGA;
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
public class CategorieDTO implements Serializable {

  private long id;
  private String libelle;
  private String pathSVG;
  private Timestamp dateCreation;
  private transient AdminOGA adminOGA;
  private transient List<Vehicule> vehicules;
}
