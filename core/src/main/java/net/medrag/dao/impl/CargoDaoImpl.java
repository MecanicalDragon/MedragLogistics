package net.medrag.dao.impl;

import net.medrag.dao.api.CargoDao;
import net.medrag.domain.entity.Cargo;
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
