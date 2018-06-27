/*
 * City
 *
 * v.1.0
 *
 * created by Stanislav Tretyakov 20.06.18
 */
package net.medrag.model.domain.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a City
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "city")
public class City implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @Column(name = "coordinates_X")
    private int coordinates_X;

    @Column(name = "coordinates_Y")
    private int coordinates_Y;

    @OneToMany(mappedBy = "currentCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<Truck>truckSet;

    @OneToMany(mappedBy = "currentCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<Driver>driverSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoordinates_X() {
        return coordinates_X;
    }

    public void setCoordinates_X(int coordinates_X) {
        this.coordinates_X = coordinates_X;
    }

    public int getCoordinates_Y() {
        return coordinates_Y;
    }

    public void setCoordinates_Y(int coordinates_Y) {
        this.coordinates_Y = coordinates_Y;
    }

    public Set<Truck> getTruckSet() {
        return truckSet;
    }

    public void setTruckSet(Set<Truck> truckSet) {
        this.truckSet = truckSet;
    }

    public Set<Driver> getDriverSet() {
        return driverSet;
    }

    public void setDriverSet(Set<Driver> driverSet) {
        this.driverSet = driverSet;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates_X=" + coordinates_X +
                ", coordinates_Y=" + coordinates_Y +
                '}';
    }
}
