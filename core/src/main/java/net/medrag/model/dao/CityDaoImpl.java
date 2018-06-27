package net.medrag.model.dao;

import net.medrag.model.domain.entity.City;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class CityDaoImpl<E extends City> extends EntityDaoImpl<E> implements CityDao<E> {
}
