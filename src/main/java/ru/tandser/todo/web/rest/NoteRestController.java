package ru.tandser.todo.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.tandser.todo.domain.Note;
import ru.tandser.todo.web.controller.AbstractNoteController;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(NoteRestController.REST_PATH)
public class NoteRestController extends AbstractNoteController {

    public static final String REST_PATH = "/rest/notes";

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

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> saveWithLocation(@RequestBody Note note) {
        Note created = save(note);

        URI newResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_PATH + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(newResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Override
    public void update(@RequestBody Note note, @PathVariable int id) {
        super.update(note, id);
    }

    @PutMapping("/toggle/{id}")
    @Override
    public void toggle(@PathVariable int id, @RequestParam boolean state) {
        super.toggle(id, state);
    }
}