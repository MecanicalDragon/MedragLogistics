package net.medrag.model.service;

import net.medrag.dto.UserDto;
import net.medrag.model.dao.MedragRepositoryException;
import net.medrag.model.dao.UserDao;
import net.medrag.model.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private UserDao<User> userDao;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String implementation = "UserDaoImpl";

    @Autowired
    public void setUserDao(UserDao<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addNewUser(User user) {
        userDao.addEntity(user);
    }

    @Override
    @Transactional
    public User getUser(Integer id) {
        return userDao.getEntityById(new User(), id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateEntityStatus(user);
    }
}
