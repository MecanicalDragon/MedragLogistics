package net.medrag.domain.dto;

/**
 * Data Transfer Object of {Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class DriverDto implements Dto {

    private Integer id;

    private String personalNumber;

    private String name;

    private String surname;

    private String email;

    private Integer workedTime;

    private Integer paidTime;

    private Integer hoursLastMonth;

    private Long lastChange;

    private String state;

    private String previousState;

    private Integer cityId;

    private String cityName;

    private Integer destinationId;

    private String destinationName;

    private TruckDto currentTruck;

//    private Set<WaypointDto> route;


    public Integer getHoursLastMonth() {
        return hoursLastMonth;
    }

    public void setHoursLastMonth(Integer hoursLastMonth) {
        this.hoursLastMonth = hoursLastMonth;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }
//
//    public Set<WaypointDto> getRoute() {
//        return route;
//    }
//
//    public void setRoute(Set<WaypointDto> route) {
//        this.route = route;
//    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWorkedTime() {
        return workedTime;
    }

    public void setWorkedTime(Integer workedTime) {
        this.workedTime = workedTime;
    }

    public Integer getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Integer paidTime) {
        this.paidTime = paidTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public TruckDto getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(TruckDto currentTruck) {
        this.currentTruck = currentTruck;
    }

    @Override
    public String toString() {
        return "DriverDto{" +
                "id=" + id +
                ", personalNumber='" + personalNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", workedTime=" + workedTime +
                ", paidTime=" + paidTime +
                ", state='" + state + '\'' +
                ", cityName='" + cityName + '\'' +
                ", lastChange='" + lastChange + '\'' +
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
        DriverDto that = (DriverDto) o;

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
