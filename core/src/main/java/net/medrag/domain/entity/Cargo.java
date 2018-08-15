package net.medrag.domain.entity;

import net.medrag.domain.enums.CargoState;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a Cargo
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "cargo")
public class Cargo extends Identifier {

    @NaturalId
    @Column(name = "cargo_index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum('prepared', 'on_board', 'delivered', 'transient', 'destination')")
    private CargoState state;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer owner;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "order_id")
    private Orderr orderr;

    @ManyToOne
    @JoinColumn(name = "departure_id")
    private City departure;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private City destination;

    @ManyToOne
    @JoinColumn(name = "current_city_id")
    private City currentCity;

    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private Set<Waypoint> route;

    @ManyToOne
    @JoinColumn(name = "current_truck_id")
    private Truck currentTruck;


    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    public Set<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(Set<Waypoint> route) {
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