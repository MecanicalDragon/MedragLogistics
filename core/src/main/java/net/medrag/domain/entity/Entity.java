package net.medrag.domain.entity;

import java.io.Serializable;

/**
 * Marker-interface, that marks entities.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface Entity extends Serializable {

    @Override
    String toString();
}
