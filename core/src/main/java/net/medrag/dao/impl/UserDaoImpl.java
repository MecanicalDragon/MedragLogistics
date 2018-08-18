package net.medrag.dao.impl;

import net.medrag.dao.api.UserDao;
import net.medrag.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link User}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class UserDaoImpl<E extends User> extends EntityDaoImpl<E> implements UserDao<E> {
}
