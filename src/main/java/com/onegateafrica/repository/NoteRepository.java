package com.onegateafrica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onegateafrica.entity.Note;
import com.onegateafrica.entity.Notification;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  Note findByUtilisateurIdAndVehiculeId(long id,long d1);


}
