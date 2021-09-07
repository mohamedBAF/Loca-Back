package com.onegateafrica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onegateafrica.dto.CategorieDTO;
import com.onegateafrica.dto.OptionDTO;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.repository.CategorieRepository;
import com.onegateafrica.service.CategorieService;
import com.onegateafrica.service.NotificationService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class CategorieController {

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  CategorieService categorieService;

  @Autowired
  CategorieRepository categorieRepository;



  public CategorieController(CategorieService categorieService) {
    super();
    this.categorieService = categorieService;
  }

  @PostMapping("/add-categorie")
  @ResponseBody
  public ResponseEntity<Object> addCategories(@RequestBody CategorieDTO categorieDTO) {
    Categorie categorieReq = modelMapper.map(categorieDTO, Categorie.class);
    ResponseEntity<Categorie> categorie = categorieService.addCategorie(categorieReq);
    if (categorie.getStatusCodeValue() == 200) {
      CategorieDTO categorieRes = modelMapper.map(categorie.getBody(), CategorieDTO.class);
      return new ResponseEntity<>(categorieRes, HttpStatus.OK);
    } else if (categorie.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
    }
  }


  @DeleteMapping("/remove-categorie/{categorie-id}")
  @ResponseBody
  public void deletecategorie(@PathVariable("categorie-id") long categorieId) {

    categorieService.deleteCategorie(categorieId);
  }

  @GetMapping("/retrieve-categorie/{categorie-id}")
  @ResponseBody
  public ResponseEntity<Object> retrieveCategorie(@PathVariable("categorie-id") long categorieId) {

    ResponseEntity<Categorie> categorie = categorieService.retrieveCategorie(categorieId);
    if (categorie.getStatusCodeValue() == 200) {
      OptionDTO optDto = modelMapper.map(categorie.getBody(), OptionDTO.class);
      return new ResponseEntity<>(optDto, HttpStatus.OK);
    } else if(categorie.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }


  @GetMapping("/retrieve-all-categories")
  @ResponseBody
  public List<CategorieDTO> getCategories() {
    return categorieService.retrieveAllCategories().stream().map(categorie -> modelMapper.map(categorie, CategorieDTO.class))
        .collect(Collectors.toList());

  }


  @PutMapping("/modify-categorie/{categorie-id}")
  @ResponseBody
  public ResponseEntity<Object> modifyCategorie(@PathVariable("categorie-id") long categorieId, @RequestBody CategorieDTO categorieDTO) {

    Categorie categorieReq = modelMapper.map(categorieDTO, Categorie.class);
    ResponseEntity<Categorie> categorie = categorieService.updateCategorie(categorieId, categorieReq);

    if (categorie.getStatusCodeValue() == 200) {
      CategorieDTO categorieRes = modelMapper.map(categorie.getBody(), CategorieDTO.class);
      return new ResponseEntity<>(categorieRes, HttpStatus.OK);
    } else if (categorie.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(categorie.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }

}
