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
    private int id;

    @NaturalId
    @Column(name = "cargo_number")
    private String cargo_number;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "enum('prepared','on_board', 'delivered')")
    private CargoState state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo_number() {
        return cargo_number;
    }

    public void setCargo_number(String cargo_number) {
        this.cargo_number = cargo_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", cargo_number='" + cargo_number + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", state=" + state +
                '}';
    }
}