package net.medrag.model.dto;

import net.medrag.model.domain.enums.DriverState;

public class DriverDto {

    private int id;

    private String personalNumber;

    private String name;

    private String surname;

    private int workedTime;

    private DriverState state;

    private CityDto currentCity;

    private TruckDto currentTruck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(int workedTime) {
        this.workedTime = workedTime;
    }

    public DriverState getState() {
        return state;
    }

    public void setState(DriverState state) {
        this.state = state;
    }

    public CityDto getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CityDto currentCity) {
        this.currentCity = currentCity;
    }

    public TruckDto getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(TruckDto currentTruck) {
        this.currentTruck = currentTruck;
    }

    @Override
    public String toString() {
        return "DriverDto{" +
                "id=" + id +
                ", personalNumber='" + personalNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", workedTime=" + workedTime +
                ", state=" + state +
                ", currentCity=" + currentCity.getName() +
                ", currentTruck=" + currentTruck.getRegNumber() +
                '}';
    }
}
