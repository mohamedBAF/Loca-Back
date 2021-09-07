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
import org.springframework.web.bind.annotation.RestController;

import com.onegateafrica.dto.OptionDTO;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.service.OptionService;
import com.onegateafrica.service.PersonelAgenceService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionController {

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";

  @Autowired
  private OptionService optionService;
  @Autowired
  private ModelMapper modelMapper;

  public OptionController(OptionService optionService) {
    super();
    this.optionService = optionService;
  }

  //create option
  @PostMapping("/addOption")
  public ResponseEntity<Object> addOption(@RequestBody OptionDTO optionDTO) {
    Opt optReq = modelMapper.map(optionDTO, Opt.class);
    ResponseEntity<Opt> opt = optionService.addOption(optReq);
    if (opt.getStatusCodeValue() == 200) {
      OptionDTO optionRes = modelMapper.map(opt.getBody(), OptionDTO.class);
      return new ResponseEntity<>(optionRes, HttpStatus.OK);
    } else if (opt.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.FOUND);
    }
  }


  //get all options
  @GetMapping("/options")
  public List<OptionDTO> getOptions() {
    return optionService.getOptions().stream().map(option -> modelMapper.map(option, OptionDTO.class))
        .collect(Collectors.toList());
  }

  //get option by id
  @GetMapping(value = "/option/{id}")
  public ResponseEntity<Object> getOption(@PathVariable("id") long id) {
    ResponseEntity<Opt> opt = optionService.getOption(id);
    if (opt.getStatusCodeValue() == 200) {
      OptionDTO optDto = modelMapper.map(opt.getBody(), OptionDTO.class);
      return new ResponseEntity<>(optDto, HttpStatus.OK);
    } else if(opt.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }
  }

  //update option
  @PutMapping(value = "/option/{id}")
  public ResponseEntity<Object> updateOption(@PathVariable("id") long id, @RequestBody OptionDTO optionDTO) {
    Opt optReq = modelMapper.map(optionDTO, Opt.class);
    ResponseEntity<Opt> opt = optionService.updateOption(id, optReq);

    if (opt.getStatusCodeValue() == 200) {
      OptionDTO optRes = modelMapper.map(opt.getBody(), OptionDTO.class);
      return new ResponseEntity<>(optRes, HttpStatus.OK);
    } else if (opt.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
    } else if(opt.getStatusCodeValue() == 404){
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }else{
      return new ResponseEntity<>(NULL,HttpStatus.OK);

    }

  }

  //delete option
  @DeleteMapping(value = "/option/{id}")
  public void deleteOption(@PathVariable("id") long id) {
    optionService.deleteOption(id);
  }
}
