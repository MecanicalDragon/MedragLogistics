package net.medrag.domain.enums;

import net.medrag.domain.entity.Driver;

/**
 * Enum of field state from domain object {@link Driver}.
 * Needed for indicate driver status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum DriverState {
    REST,
    ON_SHIFT,
    DRIVING,
    PORTER,
    READY_TO_ROUTE
}
