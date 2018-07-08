package net.medrag.model.domain.enums;

import net.medrag.model.domain.entity.Truck;

/**
 * Enum of field state from domain object {@link Truck}.
 * Needs for indicate truck status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum TruckState {

    IN_REPAIR("In repair"),
    IN_USE("In use");

    private String status;

    TruckState(String status){
        this.status = status;
    }

    public String getTruckState(){
        return status;
    }
}
