package net.medrag.model.domain.entity;

import javax.persistence.*;

/**
 * Typical superclass for all entities
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@MappedSuperclass
public abstract class Identifier implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;

        if (getId() != null) {
            return getId().equals(that.getId());
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }
}