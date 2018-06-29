package net.medrag.model.domain.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a Truck
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "truck")
public class Truck implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NaturalId
    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "brigade_str")
    private Integer brigadeStr;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "state")
    private boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_city_id", nullable = false)
    private City currentCity;

    @OneToMany(mappedBy = "currentTruck", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<Driver> driverSet;

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

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", brigadeStr=" + brigadeStr +
                ", capacity=" + capacity +
                ", state=" + state +
                ", currentCity=" + currentCity.getName() +
                '}';
    }
}
