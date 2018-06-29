package net.medrag.model.service;

import net.medrag.model.domain.entity.User;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface UserService {
    void addUser(User user);
    User getUserByName(String name);
}
