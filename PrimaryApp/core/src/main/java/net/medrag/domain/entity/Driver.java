package net.medrag.domain.entity;

import net.medrag.domain.enums.DriverState;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Simple JavaBean domain object, that represents a Driver
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "DRIVER")
public class Driver extends Identifier {

    @NaturalId
    @Column(name = "PERSONAL_NUMBER")
    private String personalNumber;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WORKED_TIME")
    private Integer workedTime;

    @Column(name = "PAID_TIME")
    private Integer paidTime;

    @Column(name = "HOURS_LAST_MONTH")
    private Integer hoursLastMonth;

    @Column(name = "LAST_STATUS_CHANGE")
    private Long lastChange;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", columnDefinition = "ENUM('REST', 'ON_SHIFT', 'DRIVING', 'PORTER', 'READY_TO_ROUTE)")
    private DriverState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "PREVIOUS_STATE", columnDefinition = "ENUM('REST', 'ON_SHIFT', 'DRIVING', 'PORTER', 'READY_TO_ROUTE)")
    private DriverState previousState;

    @ManyToOne
    @JoinColumn(name = "CURRENT_CITY_ID")
    private City city;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_CITY_ID")
    private City destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_TRUCK_ID")
    private Truck currentTruck;

//    @ManyToMany(mappedBy = "brigade", fetch = FetchType.LAZY)
//    private Set<Waypoint> route;

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public DriverState getPreviousState() {
        return previousState;
    }

    public void setPreviousState(DriverState previousState) {
        this.previousState = previousState;
    }
//
//    public Set<Waypoint> getRoute() {
//        return route;
//    }
//
//    public void setRoute(Set<Waypoint> route) {
//        this.route = route;
//    }


    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public Integer getHoursLastMonth() {
        return hoursLastMonth;
    }

    public void setHoursLastMonth(Integer hoursLastMonth) {
        this.hoursLastMonth = hoursLastMonth;
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

    public City getCity() {
        return city;
    }

    public void setCity(City currentCity) {
        this.city = currentCity;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    @Override
    public String toString() {
        return String.format("Driver{id=%d, personalNumber='%s', name='%s', surname='%s', email='%s', workedTime=%d, paidTime=%d, state=%s}", id, personalNumber, name, surname, email, workedTime, paidTime, state);
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
