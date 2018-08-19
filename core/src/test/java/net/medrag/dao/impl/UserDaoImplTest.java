package net.medrag.dao.impl;

import net.medrag.dao.api.UserDao;
import net.medrag.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    private UserDao<User> userDao;

    @Test
    public void nothingToTest() throws Exception {
        userDao = new UserDaoImpl<>();
    }

}