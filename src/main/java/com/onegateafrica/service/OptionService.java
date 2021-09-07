package com.onegateafrica.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegateafrica.entity.Opt;
import com.onegateafrica.repository.OptionRepository;

@Service
public class OptionService {

  @Autowired
  private OptionRepository optionRepository;


  //create Option
  public ResponseEntity<Opt>  addOption(Opt opt) {
    if (opt == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    opt.setDateCreation(new Timestamp(System.currentTimeMillis()));
    opt.setDateUpdate(new Timestamp(System.currentTimeMillis()));
    optionRepository.save(opt);
    return ResponseEntity.ok(opt);
  }


  //get all Options
  public List<Opt> getOptions() {
    return optionRepository.findAll();
  }

  //get Option by id
  public ResponseEntity<Opt> getOption(long id) {

    Optional<Opt> optionalOption = optionRepository.findById(id);
    if (optionalOption.isPresent()) {
      return ResponseEntity.ok(optionalOption.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //update Option
  public ResponseEntity<Opt> updateOption(long id, Opt opt) {

    if (opt == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Opt> optionalOption = optionRepository.findById(id);
    if (optionalOption.isPresent()) {
      opt.setId(id);
      opt.setDateCreation(opt.getDateCreation());
      opt.setDateUpdate(new Timestamp(System.currentTimeMillis()));
      optionRepository.save(opt);
      return ResponseEntity.ok(optionalOption.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  //delete Option
  public void deleteOption(long id) {

    optionRepository.deleteById(id);
  }

}
