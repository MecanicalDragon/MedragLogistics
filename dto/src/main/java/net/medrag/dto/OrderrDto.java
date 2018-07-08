package net.medrag.dto;

import java.util.List;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class OrderrDto implements Dto {

    private Integer id;

    private String orderIndex;

    private Boolean implemented;

    private CustomerDto owner;

    private List<WaypointDto> waypoints;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getImplemented() {
        return implemented;
    }

    public void setImplemented(Boolean implemented) {
        this.implemented = implemented;
    }

    public List<WaypointDto> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointDto> waypoints) {
        this.waypoints = waypoints;
    }

    public CustomerDto getOwner() {
        return owner;
    }

    public void setOwner(CustomerDto owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "OrderrDto{" +
                "id=" + id +
                ", orderIndex='" + orderIndex + '\'' +
                ", implemented=" + implemented +
                ", owner=" + owner.getName() +
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
        OrderrDto that = (OrderrDto) o;

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
