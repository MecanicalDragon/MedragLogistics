package net.medrag.model.service.dto;

import net.medrag.model.dao.MedragRepositoryException;
import net.medrag.model.domain.dto.UserDto;
import net.medrag.model.domain.entity.User;
import net.medrag.model.service.MedragServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class UserServiceImpl <D extends UserDto, E extends User> extends DTOServiceImpl<D, E>
        implements UserService<D, E>  {

    private static final String implementation = "userDaoImpl";

    @Override
    @Transactional
    public void addNewUser(User user)throws MedragServiceException {
        try {
            entityDao.addEntity((E)user);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    @Override
    @Transactional
    public User getUser(Integer id)throws MedragServiceException {
        try {
            return entityDao.getEntityById((E)new User(), id);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user)throws MedragServiceException {
        try {
            entityDao.updateEntityStatus((E)user);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user)throws MedragServiceException {
        try {
            entityDao.removeEntity((E)user);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

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
