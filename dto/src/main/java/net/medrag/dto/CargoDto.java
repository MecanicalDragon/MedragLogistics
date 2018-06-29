package net.medrag.dto;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoDto implements Dto {

    private Integer id;

    private String cargo_number;

    private String name;

    private Integer weight;

    private String state;

    private CustomerDto customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CargoDto{" +
                "id=" + id +
                ", cargo_number='" + cargo_number + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", state='" + state + '\'' +
                ", customer=" + customer +
                '}';
    }
}
