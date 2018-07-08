package net.medrag.model.domain.entity;

import net.medrag.model.domain.enums.DriverState;
import org.hibernate.annotations.NaturalId;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Simple JavaBean domain object, that represents a Driver
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "driver")
public class Driver extends Identifier {

    @NaturalId
    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "worked_time")
    private Integer workedTime;

    @Column(name = "paid_time")
    private Integer paidTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum('rest', 'on_shift', 'driving', 'porter')")
    private DriverState state;

    @ManyToOne
    @JoinColumn(name = "current_city_id", nullable = false)
    private City currentCity;

    @ManyToOne
    @JoinColumn(name = "current_truck_id", nullable = false)
    private Truck currentTruck;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(Integer workedTime) {
        this.workedTime = workedTime;
    }

    public Integer getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Integer paidTime) {
        this.paidTime = paidTime;
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
                ", personalNumber='" + personalNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", workedTime=" + workedTime +
                ", paidTime=" + paidTime +
                ", state=" + state +
                ", currentCity=" + currentCity.getName() +
                ", currentTruck=" + currentTruck.getRegNumber() +
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
        Driver that = (Driver) o;

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
