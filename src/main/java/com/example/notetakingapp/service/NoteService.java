package com.example.notetakingapp.service;

import com.example.notetakingapp.dao.NoteRepository;
import com.example.notetakingapp.model.Note;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public Note postNote(Note note) {
        return repository.save(note);
    }

    public Note getNote(long id) {
        return repository.findById(id).orElseThrow();
    }

    public Note patchNote(long id, Map<String, Object> map) {

        Note foundNote = repository.findById(id).get();

        map.forEach((k, v) -> {
            switch(k) {
                case "title":
                    foundNote.setTitle((String) v);
                    break;
                case "content":
                    foundNote.setContent((String) v);
                    break;
                default:
                    break;
            }
        });

        return repository.save(foundNote);
    }

    public String deleteNote(long id) {
        repository.deleteById(id);
        return String.format("Note %d was deleted", id);

    }

    public Iterable<Note> getAllNotes() {
        return repository.findAll();
    }
}
