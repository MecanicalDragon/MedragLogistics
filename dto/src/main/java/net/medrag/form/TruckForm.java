package net.medrag.form;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class TruckForm {

    private String regNumber;

    private String brigadeStr;

    private String capacity;

    private String state;

    private String currentCity;



    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getBrigadeStr() {
        return brigadeStr;
    }

    public void setBrigadeStr(String brigadeStr) {
        this.brigadeStr = brigadeStr;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public String toString() {
        return "TruckForm{" +
                "regNumber='" + regNumber + '\'' +
                ", brigadeStr='" + brigadeStr + '\'' +
                ", capacity='" + capacity + '\'' +
                ", state='" + state + '\'' +
                ", currentCity='" + currentCity + '\'' +
                '}';
    }
}
