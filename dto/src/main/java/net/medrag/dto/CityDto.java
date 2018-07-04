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

    private Integer coordinates_X;

    private Integer coordinates_Y;

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

    public Integer getCoordinates_X() {
        return coordinates_X;
    }

    public void setCoordinates_X(Integer coordinates_X) {
        this.coordinates_X = coordinates_X;
    }

    public Integer getCoordinates_Y() {
        return coordinates_Y;
    }

    public void setCoordinates_Y(Integer coordinates_Y) {
        this.coordinates_Y = coordinates_Y;
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
                ", coordinates_X=" + coordinates_X +
                ", coordinates_Y=" + coordinates_Y +
                ", index='" + index + '\'' +
                '}';
    }
}
