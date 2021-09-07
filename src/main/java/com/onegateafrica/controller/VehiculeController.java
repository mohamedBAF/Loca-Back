package com.onegateafrica.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onegateafrica.dto.OptionDTO;
import com.onegateafrica.dto.UtilisateurDTO;
import com.onegateafrica.dto.VehiculeDTO;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.VehiculeRepository;
import com.onegateafrica.service.UtilisateurService;
import com.onegateafrica.service.VehiculeService;
import com.sipios.springsearch.anotation.SearchSpec;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class VehiculeController {

  @Autowired
  VehiculeRepository vehiculeRepository;
  @Autowired
  VehiculeService vehiculeService;
  @Autowired
  private ModelMapper modelMapper;

  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";
  public final static String BAD_REQUEST = "BAD_REQUEST";

  public VehiculeController(VehiculeService vehiculeService) {
    super();
    this.vehiculeService = vehiculeService;
  }


  @PutMapping("/uploadImageVeh/{id}")
  public ResponseEntity<Object> uploadImageVeh(@PathVariable("id") long id,
      @RequestParam("image") MultipartFile file) {
    ResponseEntity<Vehicule> vehicule = vehiculeService.uploadImageVeh(id,file);
    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeDTO = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }

  @PostMapping(value="/addVehiculeByUtilisateur/{id}", consumes={"application/json"})
  public ResponseEntity<Object> addVehiculeByUtilisateur(@PathVariable("id") long id, @RequestBody Vehicule vehiculep){
    ResponseEntity<Vehicule> vehicule = vehiculeService.addVehiculeByUtilisateur(id,vehiculep);
    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeDTO = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }


  }

  /**1505**/
  //get all Vehicules
  @GetMapping("/AllVehicules")
  public List<VehiculeDTO> getVehicules() {
    return vehiculeService.getVehicules().stream().map(vehicule -> modelMapper.map(vehicule, VehiculeDTO.class))
        .collect(Collectors.toList());
  }
  //filtrage
  @GetMapping("/vehicules") /* ?search=(annee:"2015") OR (gamme:"clio") AND (categorie.libelle:"voiture mariage") */
  public ResponseEntity<List<Vehicule>> searchForVehicules(@SearchSpec Specification<Vehicule> specs) {
    return new ResponseEntity<>(vehiculeRepository.findAll(Specification.where(specs)), HttpStatus.OK);
  }
  /**end 1505**/
  /***1504***/


  @GetMapping("/rechercherVehicule/{prixMin}/{prixMax}/{ville}/{dateDebut}/{dateFin}")
  public List<VehiculeDTO> RechercheVehicule(@PathVariable("prixMin") float prixMin,@PathVariable("prixMax") float prixMax,@PathVariable("ville") String ville,@PathVariable("dateDebut") @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd") Date dateDebut,@PathVariable("dateFin")  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd") Date dateFin) {
    //LocalDate dateDebutt = LocalDate.parse(dateDebut, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    //LocalDate dateFinn = LocalDate.parse(dateFin, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    return vehiculeService.RechercheVehicule(prixMin,prixMax,ville,dateDebut,dateFin).stream().map(vehicule -> modelMapper.map(vehicule, VehiculeDTO.class))
        .collect(Collectors.toList());
  }

  /***1500 => 1502***/

  @GetMapping(value = "/getAllVehiculeByIdUtilisateur/{id}")
  public List<VehiculeDTO> getAllVehiculeByIdUtilisateur(@PathVariable("id") long id) {
    return vehiculeService.getAllVehiculeByIdUtilisateur(id).stream().map(vehicule -> modelMapper.map(vehicule, VehiculeDTO.class))
        .collect(Collectors.toList());
  }
  //delete option
  @DeleteMapping(value = "/deleteVehicule/{id}")
  public void deleteVehicule(@PathVariable("id") long id) {
    vehiculeService.deleteVehicule(id);
  }


  @GetMapping(value = "/getVehicule/{id}")
  public ResponseEntity<Object> getVehicule(@PathVariable("id") long id) {
    ResponseEntity<Vehicule> vehicule = vehiculeService.getVehicule(id);
    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeDTO = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeDTO, HttpStatus.OK);
    } else if(vehicule.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);


  /**1504**/
    }
  }

  @PutMapping(value = "/putVehicule/{id}")
  public ResponseEntity<Object> updateOption(@PathVariable("id") long id, @RequestBody VehiculeDTO vehiculeDTO) {
    Vehicule vehiculeReq = modelMapper.map(vehiculeDTO, Vehicule.class);
    ResponseEntity<Vehicule> vehicule = vehiculeService.updateVehicule(id, vehiculeReq);

    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeRes = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeRes, HttpStatus.OK);
    } else if (vehicule.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(vehicule.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }



  @PutMapping(value = "/putVehiculeDates/{id}/{dateDebutDemande}/{dateFinDemande}")
  public ResponseEntity<Object> updateVehiculeDates(@PathVariable("id") long id,@PathVariable("dateDebutDemande") @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd") Date dateDebutDemande,@PathVariable("dateFinDemande") @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd") Date dateFinDemande, @RequestBody VehiculeDTO vehiculeDTO) {
    Vehicule vehiculeReq = modelMapper.map(vehiculeDTO, Vehicule.class);
    ResponseEntity<Vehicule> vehicule = vehiculeService.updateVehiculeDates(id,dateDebutDemande,dateFinDemande, vehiculeReq);

    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeRes = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeRes, HttpStatus.OK);
    } else if (vehicule.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(vehicule.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }





  /*** END 1500=>1502***/

  @GetMapping("/RefreshVehicule")
  public List<VehiculeDTO> RefreshVehiculeDispo() {
    return vehiculeService.RefreshVehiculeDispo().stream().map(vehicule -> modelMapper.map(vehicule, VehiculeDTO.class))
        .collect(Collectors.toList());
  }

  /***1601**/
  @GetMapping(value = "/getAllVehiculeByIdAgence/{id}")
  public List<VehiculeDTO> getAllVehiculeByIdAgence(@PathVariable("id") long id) {
    return vehiculeService.getAllVehiculeByIdAgence(id).stream().map(vehicule -> modelMapper.map(vehicule, VehiculeDTO.class))
        .collect(Collectors.toList());
  }

  @PostMapping(value="/addVehiculeByPersonnel/{id}", consumes={"application/json"})
  public ResponseEntity<Object> addVehiculeByPersonnel(@PathVariable("id") long id, @RequestBody Vehicule vehiculep){
    ResponseEntity<Vehicule> vehicule = vehiculeService.addVehiculeByPersonnel(id,vehiculep);
    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeDTO = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }


  }
  @GetMapping(value = "/getAllVehiculesByIdAgence/{id}")
  public List<VehiculeDTO> getAllVehiculesByIdAgence(@PathVariable("id") long id) {
    return vehiculeService.getAllVehiculesByIdAgence(id).stream().map(vehicule -> modelMapper.map(vehicule, VehiculeDTO.class))
        .collect(Collectors.toList());
  }





  @PutMapping(value = "/updateVehiculeViews/{id}")
  public ResponseEntity<Object> updateVehiculeViews(@PathVariable("id") long id) {

    ResponseEntity<Vehicule> vehicule = vehiculeService.updateVehiculeViews(id);

    if (vehicule.getStatusCodeValue() == 200) {
      VehiculeDTO vehiculeRes = modelMapper.map(vehicule.getBody(), VehiculeDTO.class);
      return new ResponseEntity<>(vehiculeRes, HttpStatus.OK);
    } else if (vehicule.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(vehicule.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }

}

