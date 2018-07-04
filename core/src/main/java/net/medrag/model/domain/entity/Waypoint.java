package net.medrag.model.domain.entity;

import net.medrag.model.domain.enums.WaypointType;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a waypoint
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "waypoint")
public class Waypoint implements Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orderr orderr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "wp_type", columnDefinition = "enum('load', 'unload')")
    private WaypointType wayPointType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck currentTruck;

    @ManyToMany
    @JoinTable(name = "waypoint_drivers",
            joinColumns = @JoinColumn(name = "waypoint_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"))
    private Set<Driver> brigade;

    public Orderr getOrderr() {
        return orderr;
    }

    public void setOrderr(Orderr orderr) {
        this.orderr = orderr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public WaypointType getWayPointType() {
        return wayPointType;
    }

    public void setWayPointType(WaypointType wayPointType) {
        this.wayPointType = wayPointType;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    public Set<Driver> getBrigade() {
        return brigade;
    }

    public void setBrigade(Set<Driver> brigade) {
        this.brigade = brigade;
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "id=" + id +
                ", cargo=" + cargo.getCargoIndex() +
                ", orderr=" + orderr.getOrderIndex() +
                ", city=" + city.getName() +
                ", wayPointType=" + wayPointType +
                '}';
    }
}
