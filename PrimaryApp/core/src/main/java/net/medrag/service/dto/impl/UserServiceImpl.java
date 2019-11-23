package net.medrag.service.dto.impl;

import net.medrag.dao.MedragRepositoryException;
import net.medrag.dao.api.UserDao;
import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.User;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service implementation for DAO interface {@link UserDao},
 * working with domain object, that represents a {@link User}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class UserServiceImpl <D extends UserDto, E extends User> extends DTOServiceImpl<D, E>
        implements UserService<D, E> {

    /**
     * String, that denotes correct implementation for generalised superclass
     */
    private static final String implementation = "userDaoImpl";

    /**
     * Adding new user to the database.
     * @param user - user.
     * @throws MedragServiceException - doesn't.
     */
    @Override
    @Transactional
    public void addNewUser(User user)throws MedragServiceException {
        try {
            entityDao.addEntity((E)user);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Getting user by id.
     * @param id - user id.
     * @return - user with {@param id} as id.
     * @throws MedragServiceException - haven't seen yet.
     */
    @Override
    @Transactional
    public User getUser(Integer id)throws MedragServiceException {
        try {
            return entityDao.getEntityById((E)new User(), id);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Updates user data.
     * @param user - user with updated data.
     * @throws MedragServiceException - if it is - that's only your fault.
     */
    @Override
    @Transactional
    public void updateUser(User user)throws MedragServiceException {
        try {
            entityDao.updateEntityStatus((E)user);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Gets user by it's username.
     * @param username - username of user (That user's name).
     * @return - user with specified username.
     * @throws MedragServiceException - well, if you want it...
     */
    @Override
    @Transactional
    public User getUserByUsername(String username)throws MedragServiceException {
        try {
            return entityDao.getEntityByNaturalId((E)new User(), username);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }
}
