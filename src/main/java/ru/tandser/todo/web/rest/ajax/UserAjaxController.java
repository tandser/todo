package ru.tandser.todo.web.rest.ajax;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.jackson.Views;
import ru.tandser.todo.web.controller.AbstractUserController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserAjaxController.REST_PATH)
public class UserAjaxController extends AbstractUserController {

    public static final String REST_PATH = "/ajax/users";

    @JsonView(Views.Website.class)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Override
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @JsonView(Views.Website.class)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    @Override
    public void remove(@PathVariable int id) {
        super.remove(id);
    }

    @PostMapping
    public void saveOrUpdate(@Valid User user) {
        if (user.isNew()) {
            save(user);
        } else {
            update(user, user.getId());
        }
    }

    @PostMapping("/profile")
    public void update(@Valid User user) {
        update(user, user.getId());
    }

    @PutMapping("/toggle/{id}")
    @Override
    public void toggle(@PathVariable int id, @RequestParam boolean state) {
        super.toggle(id, state);
    }

    @JsonView(Views.Website.class)
    @GetMapping(value = "/profile", produces = APPLICATION_JSON_VALUE)
    @Override
    public User profile() {
        return super.profile();
    }

    @PostMapping("/registration")
    @Override
    public void register(@Valid User user) {
        super.register(user);
    }
}