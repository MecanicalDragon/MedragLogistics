package net.medrag.model.dto;

import java.util.Set;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class WaypointForm implements Dto{

    private Integer id;

    private Integer cargoId;

    private String cargoIndex;

    private String cityName;

    private Integer cityId;

    private String wayPointType;

    private String complete;

    private Integer currentTruckId;

    private String currentTruckRegNumber;


    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWayPointType() {
        return wayPointType;
    }

    public void setWayPointType(String wayPointType) {
        this.wayPointType = wayPointType;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoIndex() {
        return cargoIndex;
    }

    public void setCargoIndex(String cargoIndex) {
        this.cargoIndex = cargoIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCurrentTruckId() {
        return currentTruckId;
    }

    public void setCurrentTruckId(Integer currentTruckId) {
        this.currentTruckId = currentTruckId;
    }

    public String getCurrentTruckRegNumber() {
        return currentTruckRegNumber;
    }

    public void setCurrentTruckRegNumber(String currentTruckRegNumber) {
        this.currentTruckRegNumber = currentTruckRegNumber;
    }

    @Override
    public String toString() {
        return "WaypointDto{" +
                "id=" + id +
                ", wayPointType='" + wayPointType + '\'' +
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
        WaypointForm that = (WaypointForm) o;

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
