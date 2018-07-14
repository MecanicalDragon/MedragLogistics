package net.medrag.model.service.dto;

import net.medrag.model.dto.UserDto;
import net.medrag.model.dao.UserDao;
import net.medrag.model.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String implementation = "UserDaoImpl";

    @Override
    @Transactional
    public void addNewUser(User user) {
        entityDao.addEntity((E)user);
    }

    @Override
    @Transactional
    public User getUser(Integer id) {
        return entityDao.getEntityById((E)new User(), id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityDao.updateEntityStatus((E)user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        entityDao.removeEntity((E)user);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return entityDao.getEntityByNaturalId((E)new User(), username);
    }
}
