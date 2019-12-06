package net.medrag.domain.entity;

import net.medrag.domain.enums.CargoState;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a Cargo
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "CARGO")
public class Cargo extends Identifier {

    @NaturalId
    @Column(name = "CARGO_INDEX")
    private String index;

    @Column(name = "NAME")
    private String name;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", columnDefinition = "ENUM('PREPARED', 'ON_BOARD', 'DELIVERED', 'TRANSIENT', 'DESTINATION')")
    private CargoState state;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Customer owner;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "ORDER_ID")
    private Orderr orderr;

    @ManyToOne
    @JoinColumn(name = "DEPARTURE_ID")
    private City departure;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_ID")
    private City destination;

    @ManyToOne
    @JoinColumn(name = "CURRENT_CITY_ID")
    private City currentCity;

    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private List<Waypoint> route;

    @ManyToOne
    @JoinColumn(name = "CURRENT_TRUCK_ID")
    private Truck currentTruck;


    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    public List<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(List<Waypoint> route) {
        this.route = route;
    }

    public Orderr getOrderr() {
        return orderr;
    }

    public void setOrderr(Orderr orderr) {
        this.orderr = orderr;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String cargoNumber) {
        this.index = cargoNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public City getDeparture() {
        return departure;
    }

    public void setDeparture(City departure) {
        this.departure = departure;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", cargoNumber='" + index + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", state=" + state +
                ", owner=" + owner.getPassport() +
                ", order=" + orderr.getIndex() +
                ", departure=" + departure.getName() +
                ", destination=" + destination.getName() +
                ", currentCity=" + currentCity.getName() +
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
        Cargo that = (Cargo) o;

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