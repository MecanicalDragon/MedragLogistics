package net.medrag.model.dao;

import net.medrag.model.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class UserDaoImpl<E extends User> extends EntityDaoImpl<E> implements UserDao<E> {
}
