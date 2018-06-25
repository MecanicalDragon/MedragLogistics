package net.medrag.model.dto;

import java.util.Set;

public class CityDto {

    private int id;

    private String name;

    private int coordinates_X;

    private int coordinates_Y;

    private Set<TruckDto> truckSet;

    private Set<DriverDto> driverSet;

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

    public Set<TruckDto> getTruckSet() {
        return truckSet;
    }

    public void setTruckSet(Set<TruckDto> truckSet) {
        this.truckSet = truckSet;
    }

    public Set<DriverDto> getDriverSet() {
        return driverSet;
    }

    public void setDriverSet(Set<DriverDto> driverSet) {
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
