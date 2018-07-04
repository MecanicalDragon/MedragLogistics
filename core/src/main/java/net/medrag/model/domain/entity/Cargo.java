package net.medrag.model.domain.entity;

import net.medrag.model.domain.enums.CargoState;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Simple JavaBean domain object, that represents a Cargo
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "cargo")
public class Cargo implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NaturalId
    @Column(name = "cargo_index")
    private String cargoIndex;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Float weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum('prepared','on_board', 'delivered', 'transfer_point')")
    private CargoState state;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer owner;

    @ManyToOne
    @JoinColumn(name = "departure_id")
    private City departure;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private City destination;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCargoIndex() {
        return cargoIndex;
    }

    public void setCargoIndex(String cargoNumber) {
        this.cargoIndex = cargoNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
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
                ", cargoNumber='" + cargoIndex + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", state=" + state +
                ", owner=" + owner.getPassport() +
                ", departure=" + departure.getName() +
                ", destination=" + destination.getName() +
                '}';
    }
}