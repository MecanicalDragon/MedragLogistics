package net.medrag.model.service.dto;

import net.medrag.model.dto.UserDto;
import net.medrag.model.domain.entity.User;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface UserService <D extends UserDto, E extends User> extends DTOService<D, E>{
    void addNewUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUser(Integer id);
    User getUserByUsername(String username);
}
