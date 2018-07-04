package net.medrag.model.domain.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Simple JavaBean domain object, that represents an order
 *
 * HERE AND BELOW "ORDERR" WITH DOUBLE 'R' IN THE END
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "orderr")
public class Orderr implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_index")
    private String orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Customer owner;

    @Column(name = "implemented")
    private Boolean implemented;

    @OneToMany(mappedBy = "orderr", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Waypoint> waypoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderNumber) {
        this.orderIndex = orderNumber;
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
                ", orderNumber='" + orderIndex + '\'' +
                ", owner=" + owner.getName() +
                ", implemented=" + implemented +
                '}';
    }
}
