package net.medrag.dto;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class DriverDto implements Dto {

    private int id;

    private String personalNumber;

    private String name;

    private String surname;

    private int workedTime;

    private String state;

    private String currentCity;

    private String currentTruck;



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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(String currentTruck) {
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
                ", currentCity=" + currentCity +
                ", currentTruck=" + currentTruck +
                '}';
    }
}
