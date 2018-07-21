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
import javax.persistence.Entity;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a City
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "city")
public class City extends Identifier {

    @NaturalId(mutable = true)
    @Column(name = "name")
    private String name;

    @Column(name = "coordinates_X")
    private Double coordinatesX;

    @Column(name = "coordinates_Y")
    private Double coordinatesY;

    @Column(name = "city_index")
    private String index;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Truck>truckSet;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Driver>driverSet;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(Double coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public Double getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(Double coordinatesY) {
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
                ", coordinatesX=" + coordinatesX +
                ", coordinatesY=" + coordinatesY +
                ", index='" + index + '\'' +
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
        City that = (City) o;

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
