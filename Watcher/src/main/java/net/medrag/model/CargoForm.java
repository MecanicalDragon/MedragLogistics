package net.medrag.model;


/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoForm{

    private String index;
    private String state;
    private String orderrIndex;
    private String orderrComplete;
    private String ownerPassport;
    private String destinationName;
    private String currentCityName;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderrIndex() {
        return orderrIndex;
    }

    public void setOrderrIndex(String orderrIndex) {
        this.orderrIndex = orderrIndex;
    }

    public String getOrderrComplete() {
        return orderrComplete;
    }

    public void setOrderrComplete(String orderrComplete) {
        this.orderrComplete = orderrComplete;
    }

    public String getOwnerPassport() {
        return ownerPassport;
    }

    public void setOwnerPassport(String ownerPassport) {
        this.ownerPassport = ownerPassport;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getCurrentCityName() {
        return currentCityName;
    }

    public void setCurrentCityName(String currentCityName) {
        this.currentCityName = currentCityName;
    }

    @Override
    public String toString() {
        return "CargoForm{" +
                "index='" + index + '\'' +
                ", orderrIndex='" + orderrIndex + '\'' +
                ", orderrComplete='" + orderrComplete + '\'' +
                ", ownerPassport='" + ownerPassport + '\'' +
                ", currentCityName='" + currentCityName + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", state='" + state + '\'' +
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

        if (getIndex() != null) {
            return getIndex().equals(that.getIndex());
        } else {
            return super.equals(that);
        }
    }

    @Override
    public int hashCode() {
        return getIndex() != null ? getIndex().hashCode() : super.hashCode();
    }
}
