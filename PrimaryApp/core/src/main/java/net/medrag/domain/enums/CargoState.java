package net.medrag.domain.enums;

import net.medrag.domain.entity.Cargo;

/**
 * Enum of field state from domain object {@link Cargo}.
 * Needed for indicate cargo status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum CargoState {

    TRANSIENT,
    PREPARED,
    ON_BOARD,
    DESTINATION,
    DELIVERED

}
