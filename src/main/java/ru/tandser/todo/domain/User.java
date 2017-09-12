package ru.tandser.todo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import ru.tandser.todo.jackson.Views;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = User.WITH_NOTES, attributeNodes = @NamedAttributeNode("notes"))
public class User extends AbstractEntity {

    public static final String WITH_NOTES = "User.withNotes";

    private String     name;
    private String     email;
    private String     password;
    private Role       role;
    private List<Note> notes;
    private boolean    disabled;

    public enum Role implements GrantedAuthority {
        ADMIN, USER;

        @Override
        public String getAuthority() {
            return "ROLE_" + name();
        }
    }

    @NotBlank
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @Email
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonView(Views.Rest.class)
    @NotNull
    @Length(min = 7)
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    @OrderBy("created DESC")
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Column(name = "disabled")
    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id",       getId())
                .add("name",     getName())
                .add("email",    getEmail())
                .add("role",     getRole())
                .add("disabled", getDisabled())
                .add("created",  getCreated())
                .add("updated",  getUpdated())
                .add("version",  getVersion())
                .toString();
    }
}