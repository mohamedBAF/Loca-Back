package com.onegateafrica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.ast.Not;
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
import com.onegateafrica.dto.NoteDTO;
import com.onegateafrica.dto.NotificationDTO;
import com.onegateafrica.dto.OptionDTO;
import com.onegateafrica.dto.UtilisateurDTO;
import com.onegateafrica.dto.VehiculeDTO;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Note;
import com.onegateafrica.entity.Notification;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.NoteRepository;
import com.onegateafrica.service.NoteService;
import com.onegateafrica.service.NotificationService;
import com.onegateafrica.service.OptionService;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/oauth")

@RestController
public class NoteController {


  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private NoteService  noteService;

  @Autowired
  private NoteRepository noteRepository;

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";
  public final static String NULL = "ID NULL DETECTED";


  @PostMapping("/add-note")
  @ResponseBody
  public ResponseEntity<Object> addnote(@RequestBody NoteDTO noteDTO) {

    Note noteReq = modelMapper.map(noteDTO, Note.class);
    ResponseEntity<Note> note= noteService.addNote(noteReq);
    if (note.getStatusCodeValue() == 200) {
      NoteDTO notDTO = modelMapper.map(note.getBody(), NoteDTO.class);

      return new ResponseEntity<>(notDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(NOT_FOUND,HttpStatus.OK);
    }
  }
//
  @GetMapping("/retrieve-all-Notes")
  @ResponseBody
  public List<NoteDTO> getNotes() {
    return noteService.retrieveAllNotes().stream().map(note -> modelMapper.map(note, NoteDTO.class))
        .collect(Collectors.toList());

  }


  @DeleteMapping("/remove-note/{note-id}")
  @ResponseBody
  public void deletenote(@PathVariable("note-id") long noteId) {

    noteService.deleteNote(noteId);
  }


  @GetMapping("/getComByUser/{id}/{id1}")
  public Note retrievComByUser(@PathVariable("id") long id,@PathVariable("id1") long id1) {
    return  noteRepository.findByUtilisateurIdAndVehiculeId(id,id1);


  }
}