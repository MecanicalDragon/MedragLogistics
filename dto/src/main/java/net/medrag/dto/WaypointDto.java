package net.medrag.dto;

import java.util.Set;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class WaypointDto implements Dto{

    private Integer id;

    private CargoDto cargo;

    private OrderDto order;

    private CityDto city;

    private String wayPointType;

    private TruckDto currentTruck;

    private Set<DriverDto> brigade;




    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CargoDto getCargo() {
        return cargo;
    }

    public void setCargo(CargoDto cargo) {
        this.cargo = cargo;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public String getWayPointType() {
        return wayPointType;
    }

    public void setWayPointType(String wayPointType) {
        this.wayPointType = wayPointType;
    }

    public TruckDto getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(TruckDto currentTruck) {
        this.currentTruck = currentTruck;
    }

    public Set<DriverDto> getBrigade() {
        return brigade;
    }

    public void setBrigade(Set<DriverDto> brigade) {
        this.brigade = brigade;
    }

    @Override
    public String toString() {
        return "WaypointDto{" +
                "id=" + id +
                ", cargo=" + cargo.getCargoIndex() +
                ", order=" + order.getOrderIndex() +
                ", city=" + city.getName() +
                ", wayPointType='" + wayPointType + '\'' +
                '}';
    }
}
