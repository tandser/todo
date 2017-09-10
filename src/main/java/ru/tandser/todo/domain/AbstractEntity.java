package ru.tandser.todo.domain;

import com.google.common.base.MoreObjects;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Integer> {

    private Integer       id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int           version;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Column(name = "updated")
    @UpdateTimestamp
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Version
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Transient
    @Override
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !getClass().equals(Hibernate.getClass(obj))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id",      getId())
                .add("created", getCreated())
                .add("updated", getUpdated())
                .add("version", getVersion())
                .toString();
    }
}