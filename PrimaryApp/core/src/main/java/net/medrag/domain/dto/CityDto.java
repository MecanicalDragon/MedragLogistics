package net.medrag.domain.dto;

import net.medrag.domain.entity.City;

/**
 * Data Transfer Object of {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CityDto implements Dto {

    /**
     * City id
     */
    private Integer id;

    /**
     * City name
     */
    private String name;

    /**
     * latitude
     */
    private String coordinatesX;

    /**
     * longitude
     */
    private String coordinatesY;

    /**
     * Index for marking cargoes
     */
    private String index;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(String coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public String getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(String coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "CityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinatesX=" + coordinatesX +
                ", coordinatesY=" + coordinatesY +
                ", index='" + index + '\'' +
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
        CityDto that = (CityDto) o;

        if (getId() != null) {
            return getId().equals(that.getId());
        } else {
            if (getName() != null) {
                return getName().equals(that.getName());
            } else {
                return super.equals(o);
            }
        }
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : getName().hashCode();
    }
}
