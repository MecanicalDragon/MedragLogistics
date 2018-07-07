package net.medrag.dto;

import java.util.Set;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CityDto implements Dto {

    private Integer id;

    private String name;

    private String coordinatesX;

    private String coordinatesY;

    private String index;

    private Set<TruckDto> truckSet;

    private Set<DriverDto> driverSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(String coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public String getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(String coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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
                ", coordinatesX=" + coordinatesX +
                ", coordinatesY=" + coordinatesY +
                ", index='" + index + '\'' +
                '}';
    }
}
