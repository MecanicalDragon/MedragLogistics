package net.medrag.model.service.dto;

import net.medrag.model.dto.UserDto;
import net.medrag.model.domain.entity.User;
import net.medrag.model.service.MedragServiceException;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface UserService<D extends UserDto, E extends User> extends DTOService<D, E> {

    void addNewUser(User user) throws MedragServiceException;

    void updateUser(User user) throws MedragServiceException;

    void deleteUser(User user) throws MedragServiceException;

    User getUser(Integer id) throws MedragServiceException;

    User getUserByUsername(String username) throws MedragServiceException;
}
