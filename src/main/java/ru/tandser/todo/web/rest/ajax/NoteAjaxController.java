package ru.tandser.todo.web.rest.ajax;

import org.springframework.web.bind.annotation.*;
import ru.tandser.todo.domain.Note;
import ru.tandser.todo.web.controller.AbstractNoteController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(NoteAjaxController.REST_PATH)
public class NoteAjaxController extends AbstractNoteController {

    public static final String REST_PATH = "/ajax/notes";

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Override
    public Note get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Override
    public List<Note> getAll() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    @Override
    public void remove(@PathVariable int id) {
        super.remove(id);
    }

    @PostMapping
    public void saveOrUpdate(@Valid Note note) {
        if (note.isNew()) {
            save(note);
        } else {
            update(note, note.getId());
        }
    }

    @PutMapping("/toggle/{id}")
    @Override
    public void toggle(@PathVariable int id, @RequestParam boolean state) {
        super.toggle(id, state);
    }
}