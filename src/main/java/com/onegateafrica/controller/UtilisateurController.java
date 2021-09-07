package com.onegateafrica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onegateafrica.dto.OptionDTO;
import com.onegateafrica.dto.UtilisateurDTO;
import com.onegateafrica.dto.VehiculeDTO;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.UtilisateurRepository;
import com.onegateafrica.service.UtilisateurService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateurController {

  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";
  public final static String BAD_REQUEST = "BAD_REQUEST";

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  UtilisateurService utilisateurService;

  @Autowired
  UtilisateurRepository utilisateurRepository;

  public UtilisateurController(UtilisateurService utilisateurService) {
    super();
    this.utilisateurService = utilisateurService;
  }

  @PutMapping("/addUser/{id}")
  public ResponseEntity<Object> saveUser(@PathVariable("id") long id, @RequestParam("nom") String nom, @RequestParam("prenom") String prenom,
      @RequestParam("cin") String cin, @RequestParam("codePermis") String codePermis,
      @RequestParam("img") MultipartFile file) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.addUser(id, nom, prenom, cin, codePermis, file);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    }
  }

  @PostMapping("/choixRegisterUtil/{id}")
  public ResponseEntity<Object> choixRegisterUtil(@PathVariable long id) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.utilisateurRegister(id);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    }

  }

  @PostMapping("/choixRegisterUtilNumTel/{numTel}")
  public ResponseEntity<Object> choixRegisterUtilNumTel(@PathVariable String numTel) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.utilisateurRegisterNumTel(numTel);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    }
  }

  //OGA-1407
  @GetMapping("/getAllUtilisateurs")
  public List<UtilisateurDTO> getAllUtilisateurs() {
    return utilisateurService.getAllUtilisateurs().stream().map(utilisateur -> modelMapper.map(utilisateur, UtilisateurDTO.class))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/getUtilisateur/{id}")
  public ResponseEntity<Object> getUtilisateur(@PathVariable("id") long id) {

    ResponseEntity<Utilisateur> utilisateur = utilisateurService.getUtilisateur(id);

    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.OK);
    } else if (utilisateur.getStatusCodeValue() == 404) {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NULL, HttpStatus.OK);

    }
  }

  //end OgA-1407


  //OGA-1406
  @PutMapping(value = "/banUtilisateur/{id}")
  public ResponseEntity<Object> banUtilisateur(@PathVariable("id") long id) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.banUtilisateur(id);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.OK);
    } else if (utilisateur.getStatusCodeValue() == 404) {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NULL, HttpStatus.OK);

    }
  }

  @PutMapping(value = "/unBanUtilisateursAuto")
  public List<UtilisateurDTO> unBanUtilisateurAuto() {
    return utilisateurService.unBanUtilisateursAuto().stream().map(utilisateur -> modelMapper.map(utilisateur, UtilisateurDTO.class))
        .collect(Collectors.toList());
  }

  @PutMapping(value = "/unBanUtilisateurManu/{id}")
  public ResponseEntity<Object> unBanUtilisateurManu(@PathVariable("id") long id) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.unBanUtilisateurManu(id);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.OK);
    } else if (utilisateur.getStatusCodeValue() == 404) {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NULL, HttpStatus.OK);

    }
  }

  //END OGA-1406

  @PostMapping("/add-utilisateur")
  @ResponseBody
  public ResponseEntity<Object> addutilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {

    Utilisateur utilisateurReq = modelMapper.map(utilisateurDTO, Utilisateur.class);
    ResponseEntity<Utilisateur> user = utilisateurService.addUtilisateur(utilisateurReq);
    if (user.getStatusCodeValue() == 200) {
      UtilisateurDTO userDTO = modelMapper.map(user.getBody(), UtilisateurDTO.class);

      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    }
  }


  @GetMapping("/retrieve-userByemail/{email}")
  @ResponseBody
  public ResponseEntity<Object> retrieveemail(@PathVariable("email") String email) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.retrieveEmail(email);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.OK);
    } else if (utilisateur.getStatusCodeValue() == 404) {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NULL, HttpStatus.OK);

    }
  }

  @PutMapping(value = "/verifier-utilisateur/{id}")
  public Utilisateur verifierUtilisateur(@PathVariable("id") long id) {
    return utilisateurService.verifUtilisateur(id);
  }

  //update user profile
  @PutMapping(value = "/editProfile/{id}")
  public ResponseEntity<Object> updateProfile(@PathVariable("id") long id, @RequestBody UtilisateurDTO utilisateurDTO) {
    Utilisateur utilisateurReq = modelMapper.map(utilisateurDTO, Utilisateur.class);
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.editProfile(id, utilisateurReq);

    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurRes = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurRes, HttpStatus.OK);
    } else if(utilisateur.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(utilisateur.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);
    }
  }

  @PutMapping("/uploadImageUser/{id}")
  public ResponseEntity<Object> uploadImageVeh(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
    ResponseEntity<Utilisateur> utilisateur = utilisateurService.uploadImageUser(id,file);
    if (utilisateur.getStatusCodeValue() == 200) {
      UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur.getBody(), UtilisateurDTO.class);
      return new ResponseEntity<>(utilisateurDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }


  @GetMapping("/getUtilisateurByVehiculesContains/{id}")
  public Utilisateur retrieveemail(@PathVariable("id") long id) {
    return  utilisateurRepository.getUtilisateurByVehicules(id);

  }



}