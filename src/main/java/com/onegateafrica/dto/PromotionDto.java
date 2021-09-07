package com.onegateafrica.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.PersonelAgence;
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
public class PromotionDto {

    private long id;
    private String description;
    private Timestamp dateCreation;
    private Date dateDebut;
    private Date dateFin;
    private int pourcentageReduction;
    private transient Agence agence;
    private transient Vehicule vehicule;
    private transient PersonelAgence personelAgence;
}
