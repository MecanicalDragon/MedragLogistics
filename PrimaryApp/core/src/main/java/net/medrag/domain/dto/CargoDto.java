package net.medrag.domain.dto;

import net.medrag.domain.entity.Cargo;
import net.medrag.domain.enums.CargoState;

import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object of {@link Cargo} entity.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoDto implements Dto {

    /**
     * Id parameter.
     */
    private Integer id;

    /**
     * Unique index parameter (natural id in database)
     */
    private String index;

    /**
     * Cargo name. Does't in affect on something important.
     */
    private String name;

    /**
     * Weight in kgs.
     */
    private String weight;

    /**
     * Cargo's state. {@link CargoState}
     */
    private CargoState state;

    /**
     * Cargo's owner
     */
    private CustomerDto owner;

    /**
     * Order for this cargo.
     */
    private OrderrDto orderr;

    /**
     * Id of departure city.
     */
    private Integer departureId;

    /**
     * Id of destination city.
     */
    private Integer destinationId;

    /**
     * Id of current city.
     */
    private Integer currentCityId;

    /**
     * Departure city name.
     */
    private String departureName;

    /**
     * Destination city name.
     */
    private String destinationName;

    /**
     * current city name.
     */
    private String currentCityName;

    /**
     * Current truck of cargo.
     */
    private TruckDto currentTruck;

    /**
     * Route of cargo.
     */
    private List<WaypointDto> route;



    public TruckDto getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(TruckDto currentTruck) {
        this.currentTruck = currentTruck;
    }

    public List<WaypointDto> getRoute() {
        return route;
    }

    public void setRoute(List<WaypointDto> route) {
        this.route = route;
    }

    public OrderrDto getOrderr() {
        return orderr;
    }

    public void setOrderr(OrderrDto orderr) {
        this.orderr = orderr;
    }

    public Integer getCurrentCityId() {
        return currentCityId;
    }

    public void setCurrentCityId(Integer currentCityId) {
        this.currentCityId = currentCityId;
    }

    public String getCurrentCityName() {
        return currentCityName;
    }

    public void setCurrentCityName(String currentCityName) {
        this.currentCityName = currentCityName;
    }

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

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
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
                ", owner=" + (owner == null ? "lazy" : owner.getPassport()) +
                ", order=" + (orderr == null ? "lazy" : orderr.getIndex()) +
                ", departureName='" + departureName + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", currentCityName='" + currentCityName + '\'' +
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
            return that.getName().equals(this.name) &&
                    that.getWeight().equals(this.weight) &&
                    that.getDepartureId().equals(this.departureId) &&
                    that.getDestinationId().equals(this.destinationId) &&
                    that.getCurrentCityId().equals(this.currentCityId);
        }
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }
}
