package net.medrag.service.dto.api;

import net.medrag.dao.api.UserDao;
import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.User;
import net.medrag.service.MedragServiceException;

/**
 * Standard service interface for DAO interface{@link UserDao},
 * working with domain object {@link User}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface UserService<D extends UserDto, E extends User> extends DTOService<D, E> {

    /**
     * Adding new user to the database.
     * @param user - user.
     * @throws MedragServiceException - doesn't.
     */
    void addNewUser(User user) throws MedragServiceException;

    /**
     * Updates user data.
     * @param user - user with updated data.
     * @throws MedragServiceException - if it is - that's only your fault.
     */
    void updateUser(User user) throws MedragServiceException;

    /**
     * Getting user by id.
     * @param id - user id.
     * @return - user with {@param id} as id.
     * @throws MedragServiceException - haven't seen yet.
     */
    User getUser(Integer id) throws MedragServiceException;

    /**
     * Gets user by it's username.
     * @param username - username of user (That user's name).
     * @return - user with specified username.
     * @throws MedragServiceException - well, if you want it...
     */
    User getUserByUsername(String username) throws MedragServiceException;
}
