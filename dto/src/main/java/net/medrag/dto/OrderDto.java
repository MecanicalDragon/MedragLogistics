package net.medrag.dto;

import java.util.List;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class OrderDto implements Dto {

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
        return "OrderDto{" +
                "id=" + id +
                ", orderIndex='" + orderIndex + '\'' +
                ", implemented=" + implemented +
                ", owner=" + owner.getName() +
                '}';
    }
}
