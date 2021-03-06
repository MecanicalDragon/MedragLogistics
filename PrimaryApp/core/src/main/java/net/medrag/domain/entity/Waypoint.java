package net.medrag.domain.entity;

import net.medrag.domain.enums.WaypointType;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Simple JavaBean domain object, that represents a waypoint
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "WAYPOINT")
public class Waypoint extends Identifier{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CARGO_ID", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID", nullable = false)
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "WP_TYPE", columnDefinition = "ENUM('LOAD', 'UNLOAD')")
    private WaypointType wayPointType;

    @Column(name = "COMPLETE")
    private Boolean complete;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "TRUCK_ID")
    private Truck currentTruck;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "waypoint_drivers",
//            joinColumns = @JoinColumn(name = "waypoint_id"),
//            inverseJoinColumns = @JoinColumn(name = "driver_id"))
//    private Set<Driver> brigade;

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
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
//
//    public Set<Driver> getBrigade() {
//        return brigade;
//    }
//
//    public void setBrigade(Set<Driver> brigade) {
//        this.brigade = brigade;
//    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "id=" + id +
                ", cargo=" + cargo.getIndex() +
                ", city=" + city.getName() +
                ", wayPointType=" + wayPointType +
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
        Waypoint that = (Waypoint) o;

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
