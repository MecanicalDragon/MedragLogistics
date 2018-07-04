package net.medrag.form;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CargoForm {

    private String name;

    private String weight;

    private String departure;

    private String destination;

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

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "CargoForm{" +
                ", name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
