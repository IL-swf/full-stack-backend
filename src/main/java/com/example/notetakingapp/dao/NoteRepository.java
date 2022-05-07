package com.example.notetakingapp.dao;

import com.example.notetakingapp.model.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
}
