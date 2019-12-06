package net.medrag.domain.entity;

import net.medrag.domain.enums.Manageable;
import net.medrag.domain.enums.TruckStatus;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a Truck
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "TRUCK")
public class Truck extends Identifier{

    @NaturalId(mutable = true)
    @Column(name = "REG_NUMBER")
    private String regNumber;

    @Column(name = "BRIGADE_STR")
    private Integer brigadeStr;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", columnDefinition = "ENUM('IN_USE', 'IN_SERVICE', 'STAY_IDLE')")
    private TruckStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "PREVIOUS_STATUS", columnDefinition = "ENUM('IN_USE', 'IN_SERVICE', 'STAY_IDLE')")
    private TruckStatus prevStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANAGEABLE", columnDefinition = "ENUM('TRUE', 'FALSE', 'UNCOMPLETED', 'NEED_TO_COMPLETE', 'SAVE_BRIGADE')")
    private Manageable manageable;

    @ManyToOne
    @JoinColumn(name = "CURRENT_CITY_ID")
    private City city;

    @ManyToOne
    @JoinColumn(name = "destination_city_id")
    private City destination;

    @OneToMany(mappedBy = "currentTruck", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Driver> brigade;

    @OneToMany(mappedBy = "currentTruck", fetch = FetchType.LAZY)
    private Set<Waypoint> route;


    public Manageable getManageable() {
        return manageable;
    }

    public void setManageable(Manageable manageable) {
        this.manageable = manageable;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public TruckStatus getPrevStatus() {
        return prevStatus;
    }

    public void setPrevStatus(TruckStatus prevStatus) {
        this.prevStatus = prevStatus;
    }

    public Set<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(Set<Waypoint> route) {
        this.route = route;
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

    public void setBrigadeStr(Integer brigadeStr) {
        this.brigadeStr = brigadeStr;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public Set<Driver> getBrigade() {
        return brigade;
    }

    public void setBrigade(Set<Driver> driverSet) {
        this.brigade = driverSet;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City currentCity) {
        this.city = currentCity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", brigadeStr=" + brigadeStr +
                ", capacity=" + capacity +
                ", state=" + status +
                ", currentCity=" + city.getName() +
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
        Truck that = (Truck) o;

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
