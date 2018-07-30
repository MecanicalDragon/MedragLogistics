package net.medrag.model.dto;

import java.util.Set;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoForm implements Dto {

    private Integer id;

    private String index;

    private String name;

    private String weight;

    private String state;

    private String orderrIndex;

    private Integer orderrId;

    private Boolean orderrComplete;

    private Integer ownerId;

    private String ownerPassport;

    private Integer departureId;

    private Integer destinationId;

    private Integer currentCityId;

    private String departureName;

    private String destinationName;

    private String currentCityName;

    public Boolean getOrderrComplete() {
        return orderrComplete;
    }

    public void setOrderrComplete(Boolean orderrComplete) {
        this.orderrComplete = orderrComplete;
    }

    public String getOrderrIndex() {
        return orderrIndex;
    }

    public void setOrderrIndex(String orderrIndex) {
        this.orderrIndex = orderrIndex;
    }

    public Integer getOrderrId() {
        return orderrId;
    }

    public void setOrderrId(Integer orderrId) {
        this.orderrId = orderrId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerPassport() {
        return ownerPassport;
    }

    public void setOwnerPassport(String ownerPassport) {
        this.ownerPassport = ownerPassport;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        return "CargoForm{" +
                "id=" + id +
                ", index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", state='" + state + '\'' +
                ", orderrIndex='" + orderrIndex + '\'' +
                ", orderrId=" + orderrId +
                ", ownerId=" + ownerId +
                ", ownerPassport='" + ownerPassport + '\'' +
                ", departureId=" + departureId +
                ", destinationId=" + destinationId +
                ", currentCityId=" + currentCityId +
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
        CargoForm that = (CargoForm) o;

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
