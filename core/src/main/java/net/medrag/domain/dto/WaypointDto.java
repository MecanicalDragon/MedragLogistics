package net.medrag.domain.dto;

/**
 * Data Transfer Object of {Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class WaypointDto implements Dto{

    private Integer id;

    private CargoDto cargo;

    private CityDto city;

    private String wayPointType;

    private String complete;

    private TruckDto currentTruck;

//    private Set<DriverDto> brigade;

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
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

//    public Set<DriverDto> getBrigade() {
//        return brigade;
//    }
//
//    public void setBrigade(Set<DriverDto> brigade) {
//        this.brigade = brigade;
//    }

    @Override
    public String toString() {
        return "WaypointDto{" +
                "id=" + id +
                ", cargo=" + cargo +
                ", city=" + city +
                ", wayPointType='" + wayPointType + '\'' +
                ", complete='" + complete + '\'' +
                ", currentTruck=" + currentTruck +
//                ", brigade=" + brigade +
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
        WaypointDto that = (WaypointDto) o;

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
