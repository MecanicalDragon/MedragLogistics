package net.medrag.dao.api;

import net.medrag.domain.entity.User;

/**
 * Standard DAO interface for domain object {@link User}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface UserDao<E extends User> extends EntityDao<E> {
}
