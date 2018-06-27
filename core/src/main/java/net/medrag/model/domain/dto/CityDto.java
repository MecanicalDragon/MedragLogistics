package net.medrag.model.domain.dto;

import net.medrag.model.domain.entity.City;

import java.util.Set;

/**
 * Data Transfer Object of {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CityDto implements Dto {

    private int id;

    private String name;

    private int coordinates_X;

    private int coordinates_Y;

    private Set<String> truckSet;

    private Set<String> driverSet;



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

    public Set<String> getTruckSet() {
        return truckSet;
    }

    public void setTruckSet(Set<String> truckSet) {
        this.truckSet = truckSet;
    }

    public Set<String> getDriverSet() {
        return driverSet;
    }

    public void setDriverSet(Set<String> driverSet) {
        this.driverSet = driverSet;
    }

    @Override
    public String toString() {
        return "CityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates_X=" + coordinates_X +
                ", coordinates_Y=" + coordinates_Y +
                '}';
    }
}
