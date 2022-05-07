package com.example.notetakingapp;

import com.example.notetakingapp.dao.NoteRepository;
import com.example.notetakingapp.model.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NoteControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    NoteRepository repository;


    Note NoteOne = new Note("note 1", "content 1");
    Note NoteTwo = new Note("note 2", "content 2");
    Note NoteThree = new Note("note 3", "content 3");

    @BeforeAll
    public void init() {
        repository.save(NoteOne);
        repository.save(NoteTwo);
        repository.save(NoteThree);

    }

    @Test
    @Rollback
    @Transactional
    public void postNoteSuccessfully() throws Exception {

        Note testNote = new Note();
        testNote.setTitle("Post Test");
        testNote.setContent("Test Content");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(testNote);

        MockHttpServletRequestBuilder request = post("/note")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title": "Post Test",
                        "content": "Test Content"
                        }
                        """);

        this.mvc.perform(request)
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Post Test"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(status().isOk());

    }

    @Test
    @Rollback
    @Transactional
    public void getNoteFromRepository() throws Exception {

        Note getNote = new Note();
        getNote.setTitle("Get Test");
        getNote.setContent("Get Content");

        repository.save(getNote);

        MockHttpServletRequestBuilder request = get("/note/" + getNote.getId());

        this.mvc.perform(get("/note/" + getNote.getId()))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Get Test"))
                .andExpect(jsonPath("$.content").value("Get Content"));
    }

    @Test
    @Rollback
    @Transactional
    public void patchNoteInRepository() throws Exception {

        MockHttpServletRequestBuilder request = patch("/note/" + NoteOne.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                         "title": "Patch Test",
                        "content": "Patch Content"                 
                        }
                        """);

        this.mvc.perform(request)
                .andExpect(jsonPath("$.id").value(NoteOne.getId()))
                .andExpect(jsonPath("$.title").value("Patch Test"))
                .andExpect(jsonPath("$.content").value("Patch Content"));
    }

    @Test
    @Rollback
    @Transactional
    public void deleteNoteInRepository() throws Exception {

        this.mvc.perform(delete("/note/" + NoteOne.getId()));

        this.mvc.perform(get("/note/" + NoteOne.getId()))
                .andExpect(content().string("Note not Found"));

    }

    @Test
    @Rollback
    @Transactional
    public void getAllNotesInRepository() throws Exception {

        this.mvc.perform(get("/note"))
                .andExpect(jsonPath("$.[0].id").value(NoteOne.getId()))
                .andExpect(jsonPath("$.[1].id").value(NoteTwo.getId()))
                .andExpect(jsonPath("$.[2].id").value(NoteThree.getId()));

    }

}
