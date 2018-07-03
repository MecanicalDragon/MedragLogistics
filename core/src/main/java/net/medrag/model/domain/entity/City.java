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
import java.util.Map;
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
    private Integer id;

    @NaturalId
    @Column(name = "name")
    private String name;

//    @ElementCollection
//    @MapKeyColumn(name="IMAGE_NAME")
//    @Column(name="IMAGE_FILENAME")
//    @CollectionTable(name="IMAGE_MAPPING")
//    private Map<City, Integer> distances;

    @Column(name = "coordinates_X")
    private Integer coordinatesX;

    @Column(name = "coordinates_Y")
    private Integer coordinatesY;

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

    public int getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(int coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public int getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(int coordinatesY) {
        this.coordinatesY = coordinatesY;
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
                ", coordinates_X=" + coordinatesX +
                ", coordinates_Y=" + coordinatesY +
                '}';
    }
}
