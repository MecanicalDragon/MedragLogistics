package net.medrag.model.domain.enums;

import net.medrag.model.domain.entity.Cargo;

/**
 * Enum of field state from domain object {@link Cargo}.
 * Needs for indicate cargo status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum CargoState {
    PREPARED("Ready to send"),
    ON_BOARD("On the way"),
    DELIVERED("Delivered"),
    TRANSFER_POINT("Transmitted");

    private String status;

    CargoState(String status){
        this.status = status;
    }

    public String getCargoState(){
        return status;
    }
}
