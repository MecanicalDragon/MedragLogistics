package net.medrag.model.service;

import net.medrag.model.dao.UserDao;
import net.medrag.model.domain.entity.User;
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
        userDao.addEntity(user);
    }

    @Override
    @Transactional
    public User getUserByName(String name) {
        return userDao.getEntityByNaturalId(new User(), name);
    }
}
