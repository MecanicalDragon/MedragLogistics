package net.medrag.model.dto;

import java.util.List;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class OrderrDto implements Dto {

    private Integer id;

    private String index;

    private Boolean complete;

    private CustomerDto owner;

    private List<WaypointDto> waypoints;

    private List<CargoDto> cargoes;


    public List<CargoDto> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoDto> cargoes) {
        this.cargoes = cargoes;
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

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
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
                ", index='" + index + '\'' +
                ", complete=" + complete +
                ", owner=" + owner +
                ", waypoints=" + waypoints +
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
