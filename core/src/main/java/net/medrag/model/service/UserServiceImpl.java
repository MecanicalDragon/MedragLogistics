package net.medrag.model.service;

import net.medrag.model.dao.MedragRepositoryException;
import net.medrag.model.dao.UserDao;
import net.medrag.model.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao<User> userDao;

    private BCryptPasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public void setUserDao(UserDao<User> userDao) {

        this.userDao = userDao;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            userDao.addEntity(user);
        } catch (MedragRepositoryException e) {
            logger.error("Could not add to database {}", user);
        }
    }

    @Override
    @Transactional
    public User getUserByName(String name) {
        try {
            return userDao.getEntityByNaturalId(new User(), name);
        } catch (MedragRepositoryException e) {
            logger.error("Could not get from database by username{}", name);
        }
        return null;
    }
}
