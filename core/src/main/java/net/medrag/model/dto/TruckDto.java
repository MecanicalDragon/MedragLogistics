package net.medrag.model.dto;

import java.util.Set;

public class TruckDto {

    private int id;

    private String regNumber;

    private int brigadeStr;

    private int capacity;

    private boolean state;

    private CityDto currentCity;

    private Set<DriverDto> driverSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getBrigadeStr() {
        return brigadeStr;
    }

    public void setBrigadeStr(int brigadeStr) {
        this.brigadeStr = brigadeStr;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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
                ", state=" + state +
                ", currentCity=" + currentCity.getName() +
                ", driverSet=" + driverSet +
                '}';
    }
}
