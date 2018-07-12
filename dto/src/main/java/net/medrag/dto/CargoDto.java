package net.medrag.dto;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoDto implements Dto {

    private Integer id;

    private String index;

    private String name;

    private String weight;

    private String state;

    private CustomerDto owner;

    private Integer departureId;

    private Integer destinationId;

    private String departureName;

    private String destinationName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
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

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getDepartureName() {
        return departureName;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public String toString() {
        return "CargoDto{" +
                "id=" + id +
                ", index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", state='" + state + '\'' +
                ", owner=" + owner.getPassport() +
                ", departureName='" + departureName + '\'' +
                ", destinationName='" + destinationName + '\'' +
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
