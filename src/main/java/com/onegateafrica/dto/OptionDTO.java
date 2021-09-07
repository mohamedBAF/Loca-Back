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
public class OptionDTO implements Serializable {

  private long id;
  private String libelle;
  private String pathSVG;
  private Timestamp dateCreation;
  private Timestamp dateUpdate;
  private transient AdminOGA adminOGA;
}
