package ru.tandser.todo.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.tandser.todo.domain.User;

import java.util.List;

@Transactional(readOnly = true)
public interface UsersJpaRepository extends JpaRepository<User, Integer> {

    User findOneByEmail(String email);

    @EntityGraph(User.WITH_NOTES)
    @Query("SELECT u FROM User AS u WHERE u.id = ?1")
    User findOneWithNotes(int id);

    @Transactional
    List<User> removeById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE User AS u SET u.disabled = ?2 WHERE u.id = ?1")
    int setDisabled(int id, boolean state);
}