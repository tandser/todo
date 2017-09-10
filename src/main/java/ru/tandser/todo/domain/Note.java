package ru.tandser.todo.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notes")
public class Note extends AbstractEntity {

    private String  text;
    private boolean done;
    private User    user;

    @NotBlank
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull
    @Column(name = "done")
    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id",      getId())
                .add("text",    getText())
                .add("done",    getDone())
                .add("created", getCreated())
                .add("updated", getUpdated())
                .add("version", getVersion())
                .toString();
    }
}