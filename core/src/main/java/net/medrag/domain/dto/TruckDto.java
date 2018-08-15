package net.medrag.domain.dto;

import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object of {Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class TruckDto implements Dto {

    private Integer id;

    private String regNumber;

    private String brigadeStr;

    private String capacity;

    private String status;

    private Integer cityId;

    private String cityName;

    private Integer destinationId;

    private String destinationName;

    private String manageable;

    private List<DriverDto> brigade;

    private Set<WaypointDto> route;

    private String prevStatus;



    public String getManageable() {
        return manageable;
    }

    public void setManageable(String manageable) {
        this.manageable = manageable;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getPrevStatus() {
        return prevStatus;
    }

    public void setPrevStatus(String prevStatus) {
        this.prevStatus = prevStatus;
    }

    public Set<WaypointDto> getRoute() {
        return route;
    }

    public void setRoute(Set<WaypointDto> route) {
        this.route = route;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getBrigadeStr() {
        return brigadeStr;
    }

    public void setBrigadeStr(String brigadeStr) {
        this.brigadeStr = brigadeStr;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<DriverDto> getBrigade() {
        return brigade;
    }

    public void setBrigade(List<DriverDto> brigade) {
        this.brigade = brigade;
    }

    @Override
    public String toString() {
        return "TruckDto{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", brigadeStr='" + brigadeStr + '\'' +
                ", capacity='" + capacity + '\'' +
                ", status='" + status + '\'' +
                ", cityName='" + cityName + '\'' +
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
        TruckDto that = (TruckDto) o;

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
