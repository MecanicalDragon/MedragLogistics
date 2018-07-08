package net.medrag.model.domain.enums;

import net.medrag.model.domain.entity.Truck;
import org.jetbrains.annotations.Contract;

/**
 * Enum of field state from domain object {@link Truck}.
 * Needs for indicate truck status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum TruckStatus {

    IN_USE("In use"),
    STAY_IDLE("Stay idle"),
    IN_SERVICE("In service");

    private final String status;

    TruckStatus(String status){
        this.status = status;
    }

    @Contract(pure = true)
    public String getTruckStatus(){
        return status;
    }
}
