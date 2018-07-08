package net.medrag.dto;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoDto implements Dto {

    private Integer id;

    private String cargoIndex;

    private String name;

    private Float weight;

    private String state;

    private CustomerDto owner;

    private CityDto departure;

    private CityDto destination;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCargoIndex() {
        return cargoIndex;
    }

    public void setCargoIndex(String cargoIndex) {
        this.cargoIndex = cargoIndex;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CustomerDto getOwner() {
        return owner;
    }

    public void setOwner(CustomerDto owner) {
        this.owner = owner;
    }

    public CityDto getDeparture() {
        return departure;
    }

    public void setDeparture(CityDto departure) {
        this.departure = departure;
    }

    public CityDto getDestination() {
        return destination;
    }

    public void setDestination(CityDto destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "CargoDto{" +
                "id=" + id +
                ", cargoIndex='" + cargoIndex + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", state='" + state + '\'' +
                ", owner=" + owner +
                ", departure=" + departure.getName() +
                ", destination=" + destination.getName() +
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
        CargoDto that = (CargoDto) o;

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
