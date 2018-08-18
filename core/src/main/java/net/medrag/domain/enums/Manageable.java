package net.medrag.domain.enums;

import net.medrag.domain.entity.Truck;

/**
 * Enum of field Manageable from domain object {@link Truck}.
 * Needed for indicate truck manageable status.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public enum Manageable {
    TRUE,
    FALSE,
    UNCOMPLETED,
    NEED_TO_COMPLETE,
    SAVE_BRIGADE
}
