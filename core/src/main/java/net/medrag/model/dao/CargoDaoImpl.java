package net.medrag.model.dao;

import net.medrag.model.domain.entity.Cargo;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class CargoDaoImpl<E extends Cargo> extends EntityDaoImpl<E> implements CargoDao<E> {
}
