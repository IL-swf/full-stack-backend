package com.example.notetakingapp;

import com.example.notetakingapp.dao.NoteRepository;
import com.example.notetakingapp.model.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteEntityTest {

    @Autowired
    NoteRepository repository;

    @Test
    @Rollback
    @Transactional
    public void createNoteObjectSuccessfully() {

        Note testNote = new Note();
        testNote.setTitle("Test Note");
        testNote.setContent("Very interesting notes here");

        Note newNote = repository.save(testNote);

        assertEquals(newNote.getId(), 1);
        assertEquals(newNote.getTitle(), "Test Note");
        assertEquals(newNote.getContent(), "Very interesting notes here");
    }

}
