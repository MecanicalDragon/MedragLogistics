package net.medrag.model.dao;

import net.medrag.model.domain.entity.Cargo;

/**
 * Standard DAO interface for domain object {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CargoDao<E extends Cargo> extends EntityDao<E> {
}
