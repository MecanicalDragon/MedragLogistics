package net.medrag.model.domain.entity;

import net.medrag.model.domain.enums.DriverState;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Simple JavaBean domain object, that represents a Driver
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "driver")
public class Driver implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NaturalId
    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "worked_time")
    private int workedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum('rest', 'on_shift', 'driving', 'porter')")
    private DriverState state;

    @ManyToOne
    @JoinColumn(name = "current_city_id", nullable = false)
    private City currentCity;

    @ManyToOne
    @JoinColumn(name = "current_truck_id", nullable = false)
    private Truck currentTruck;

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

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", personalNumber=" + personalNumber +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", workedTime=" + workedTime +
                ", state=" + state +
                ", currentCity=" + currentCity.getName() +
                ", currentTruck=" + currentTruck.getRegNumber() +
                '}';
    }
}
