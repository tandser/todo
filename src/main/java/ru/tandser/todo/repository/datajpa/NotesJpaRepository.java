package ru.tandser.todo.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.tandser.todo.domain.Note;

import java.util.List;

@Transactional(readOnly = true)
public interface NotesJpaRepository extends JpaRepository<Note, Integer> {

    Note findOneByIdAndUserId(int id, int userId);

    List<Note> findAllByUserId(int userId);

    @Transactional
    List<Note> removeByIdAndUserId(int id, int userId);

    @Transactional
    @Modifying
    @Query("UPDATE Note AS n SET n.done = ?2 WHERE n.id = ?1 AND n.user.id = ?3")
    int setDone(int id, boolean state, int userId);
}