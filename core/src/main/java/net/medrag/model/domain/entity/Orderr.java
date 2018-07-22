package net.medrag.model.domain.entity;

import org.hibernate.annotations.NaturalId;

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

    @NaturalId
    @Column(name = "order_index")
    private String index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Customer owner;

    @Column(name = "complete")
    private Boolean complete;

    @OneToMany(mappedBy = "orderr")
    private List<Cargo> cargoes;
//
//    @OneToMany(mappedBy = "orderr", fetch = FetchType.LAZY)
//    private List<Waypoint> waypoints;

    public List<Cargo> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<Cargo> cargoes) {
        this.cargoes = cargoes;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String orderNumber) {
        this.index = orderNumber;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean implemented) {
        this.complete = implemented;
    }
//
//    public List<Waypoint> getWaypoints() {
//        return waypoints;
//    }
//
//    public void setWaypoints(List<Waypoint> waypoints) {
//        this.waypoints = waypoints;
//    }

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
                ", complete=" + complete +
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
