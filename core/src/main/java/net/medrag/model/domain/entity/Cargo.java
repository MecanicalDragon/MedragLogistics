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
    @Column(name = "cargo_number")
    private String cargoNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum('prepared','on_board', 'delivered')")
    private CargoState state;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Customer owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCargoNumber() {
        return cargoNumber;
    }

    public void setCargoNumber(String cargoNumber) {
        this.cargoNumber = cargoNumber;
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

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", cargo_number='" + cargoNumber + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", state=" + state +
                ", owner=" + owner +
                '}';
    }
}