package net.medrag.model.domain.dto;

import net.medrag.model.domain.entity.Truck;

import java.util.Set;

/**
 * Data Transfer Object of {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class TruckDto implements Dto {

    private int id;

    private String regNumber;

    private int brigadeStr;

    private int capacity;

    private boolean state;

    private String currentCity;

    private Set<String> driverSet;



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

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public Set<String> getDriverSet() {
        return driverSet;
    }

    public void setDriverSet(Set<String> driverSet) {
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
                ", currentCity=" + currentCity +
                ", driverSet=" + driverSet +
                '}';
    }
}
