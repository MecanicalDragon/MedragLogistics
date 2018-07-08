package net.medrag.dto;

import java.util.Set;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class TruckDto implements Dto {

    private Integer id;

    private String regNumber;

    private Integer brigadeStr;

    private Integer capacity;

    private String status;

    private CityDto currentCity;

    private Set<DriverDto> driverSet;



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

    public Integer getBrigadeStr() {
        return brigadeStr;
    }

    public void setBrigadeStr(Integer brigadeStr) {
        this.brigadeStr = brigadeStr;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CityDto getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CityDto currentCity) {
        this.currentCity = currentCity;
    }

    public Set<DriverDto> getDriverSet() {
        return driverSet;
    }

    public void setDriverSet(Set<DriverDto> driverSet) {
        this.driverSet = driverSet;
    }

    @Override
    public String toString() {
        return "TruckDto{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", brigadeStr=" + brigadeStr +
                ", capacity=" + capacity +
                ", state=" + status +
                ", currentCity=" + currentCity.getName() +
                ", driverSet=" + driverSet +
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
