package ru.tandser.todo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tandser.todo.domain.Note;
import ru.tandser.todo.service.NoteService;
import ru.tandser.todo.web.Principal;

import java.util.List;

import static ru.tandser.todo.utils.Inspector.requireConsistency;
import static ru.tandser.todo.utils.Inspector.requireNew;

public abstract class AbstractNoteController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private NoteService noteService;

    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    public Note get(int id) {
        Principal principal = Principal.get();
        logger.info("{}: get({})", principal.getUsername(), id);
        return noteService.get(id, principal.getId());
    }

    public List<Note> getAll() {
        Principal principal = Principal.get();
        logger.info("{}: getAll()", principal.getUsername());
        return noteService.getAll(principal.getId());
    }

    public void remove(int id) {
        Principal principal = Principal.get();
        logger.info("{}: remove({})", principal.getUsername(), id);
        noteService.remove(id, principal.getId());
    }

    public Note save(Note note) {
        Principal principal = Principal.get();
        logger.info("{}: save({})", principal.getUsername(), note);
        requireNew(note);
        return noteService.save(note, principal.getId());
    }

    public void update(Note note, int id) {
        Principal principal = Principal.get();
        logger.info("{}: update({}, {})", principal.getUsername(), note, id);
        requireConsistency(note, id);
        noteService.update(note, principal.getId());
    }

    public void toggle(int id, boolean state) {
        Principal principal = Principal.get();
        logger.info("{}: toggle({}, {})", principal.getUsername(), id, state);
        noteService.toggle(id, state, principal.getId());
    }
}