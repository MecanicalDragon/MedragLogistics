package net.medrag.model.domain.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

/**
 * Simple JavaBean domain object, that represents an order
 *
 * HERE AND BELOW "ORDERR" WITH DOUBLE 'R' IN THE END
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "orderr")
public class Orderr extends Identifier {

    @Column(name = "order_index")
    private String index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Customer owner;

    @Column(name = "implemented")
    private Boolean implemented;

    @OneToMany(mappedBy = "orderr")
    private List<Waypoint> waypoints;

    public String getIndex() {
        return index;
    }

    public void setIndex(String orderNumber) {
        this.index = orderNumber;
    }

    public Boolean getImplemented() {
        return implemented;
    }

    public void setImplemented(Boolean implemented) {
        this.implemented = implemented;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Orderr{" +
                "id=" + id +
                ", Index='" + index + '\'' +
                ", owner=" + owner.getName() +
                ", implemented=" + implemented +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orderr that = (Orderr) o;

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
