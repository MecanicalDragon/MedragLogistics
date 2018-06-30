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

    private String orderNumber;

    private Boolean implemented;

    private List<WaypointDto> waypoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", implemented=" + implemented +
                '}';
    }
}
