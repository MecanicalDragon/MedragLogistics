package net.medrag.model.domain.enums;

import net.medrag.model.domain.entity.Driver;

/**
 * Enum of field state from domain object {@link Driver}.
 * Needs for indicate driver status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum DriverState {
    REST,
    ON_SHIFT,
    DRIVING,
    PORTER
}
