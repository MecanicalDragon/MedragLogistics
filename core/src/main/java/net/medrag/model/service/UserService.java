package net.medrag.model.service;

import net.medrag.dto.UserDto;
import net.medrag.model.domain.entity.User;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface UserService <D extends UserDto, E extends User> extends DTOService<D, E>{
    void addNewUser(User user);
    User getUser(Integer id);
    void updateUser(User user);
}
