package ru.tandser.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.repository.UserRepository;
import ru.tandser.todo.utils.Inspector;

import java.util.List;

import static ru.tandser.todo.utils.Inspector.requireExist;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository  userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(int id) {
        return requireExist(userRepository.get(id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getByEmail(String email) {
        return requireExist(userRepository.getByEmail(email));
    }

    @Override
    public User getWithNotes(int id) {
        return requireExist(userRepository.getWithNotes(id));
    }

    @Override
    public void remove(int id) {
        requireExist(userRepository.remove(id));
    }


}