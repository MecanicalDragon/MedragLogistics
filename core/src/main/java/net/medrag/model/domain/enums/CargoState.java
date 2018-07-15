package net.medrag.model.domain.enums;

import net.medrag.model.domain.entity.Cargo;
import org.jetbrains.annotations.Contract;

/**
 * Enum of field state from domain object {@link Cargo}.
 * Needs for indicate cargo status.
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
