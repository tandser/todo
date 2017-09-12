package ru.tandser.todo.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.web.controller.AbstractUserController;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserRestController.REST_PATH)
public class UserRestController extends AbstractUserController {

    public static final String REST_PATH = "/rest/users";

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Override
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/by", produces = APPLICATION_JSON_VALUE)
    @Override
    public User getByEmail(@RequestParam String email) {
        return super.getByEmail(email);
    }

    @GetMapping(value = "/details/{id}", produces = APPLICATION_JSON_VALUE)
    @Override
    public User getWithNotes(@PathVariable int id) {
        return super.getWithNotes(id);
    }

    @DeleteMapping("/{id}")
    @Override
    public void remove(@PathVariable int id) {
        super.remove(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveWithLocation(@RequestBody User user) {
        User created = save(user);

        URI newResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_PATH + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(newResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Override
    public void update(@RequestBody User user, @PathVariable int id) {
        super.update(user, id);
    }

    @PutMapping("/toggle/{id}")
    @Override
    public void toggle(@PathVariable int id, @RequestParam boolean state) {
        super.toggle(id, state);
    }
}