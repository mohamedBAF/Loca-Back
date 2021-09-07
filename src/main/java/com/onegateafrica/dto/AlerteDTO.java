package com.onegateafrica.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.AdminOGA;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.enums.Message;

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
public class AlerteDTO implements Serializable {


  private long id;
  private String recipient;
  private Message message;
  private boolean View;
  private Timestamp createdAt;
  private transient AdminOGA adminOGA;
  private transient Utilisateur utilisateur;
  private transient Agence agence;
}
