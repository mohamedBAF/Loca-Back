package com.onegateafrica.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Note;
import com.onegateafrica.entity.Notification;
import com.onegateafrica.entity.Utilisateur;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.NoteRepository;
import com.onegateafrica.repository.NotificationRepository;
import com.onegateafrica.repository.UsersRepository;
import com.onegateafrica.repository.VehiculeRepository;


@Service
public class NoteService {


  @Autowired
  private NoteRepository noteRepository;


  public ResponseEntity<Note> addNote(Note note) {
    if (note == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    note.setDateCreation(new Timestamp(System.currentTimeMillis()));

    noteRepository.save(note);
    return ResponseEntity.ok(note);

  }
  public List<Note> retrieveAllNotes() {
    List<Note> notes = (List<Note>) noteRepository.findAll();

    return notes;
  }


  public void deleteNote(long id) {
    noteRepository.deleteById(id);
  }

}

