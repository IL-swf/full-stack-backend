package com.example.notetakingapp.controller;

import com.example.notetakingapp.model.Note;
import com.example.notetakingapp.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping
    public Note postNoteToRepository(@RequestBody Note note) {
        return service.postNote(note);
    }

    @GetMapping("/{id}")
    public Note getNoteFromRepository(@PathVariable long id) {
        return service.getNote(id);
    }

    @PatchMapping("/{id}")
    public Note patchNoteRepository(@PathVariable long id, @RequestBody Map<String, Object> map) {
        return service.patchNote(id, map);
    }

    @DeleteMapping("/{id}")
    public String deleteNoteFromRepository(@PathVariable long id) {
        return service.deleteNote(id);
    }

    @GetMapping
    public Iterable<Note> getAllNotesFromRepository() {
        return service.getAllNotes();
    }
}
