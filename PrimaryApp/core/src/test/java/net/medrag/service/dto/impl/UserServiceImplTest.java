package net.medrag.service.dto.impl;

import net.medrag.dao.api.EntityDao;
import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends DTOServiceImpl {

    @Mock
    private EntityDao<User> entityDao;

    @Spy
    private UserServiceImpl<UserDto, User> userService;

    private User user;

    @Before
    public void setUp() throws Exception {

        userService = new UserServiceImpl<>();
        userService.setEntityDao(entityDao);
        user = new User();
        user.setId(1);
        user.setUsername("Faggot");

    }

    @After
    public void tearDown() throws Exception {

        userService = null;
        user = null;
    }

    @Test   //Testing adding new user
    public void addNewUser() throws Exception {

        when(entityDao.addEntity(any(User.class))).thenReturn(1);
        userService.addNewUser(user);
        verify(entityDao).addEntity(any(User.class));
    }

    @Test   //Testing extracting user
    public void getUser() throws Exception {

        when(entityDao.getEntityById(any(User.class), anyInt())).thenReturn(user);
        User extractedUser = userService.getUser(1);
        assertEquals(extractedUser, user);
    }

    @Test   //Testing updating user
    public void updateUser() throws Exception {

        doNothing().when(entityDao).updateEntityStatus(any(User.class));
        userService.updateUser(user);
        verify(entityDao).updateEntityStatus(any(User.class));
    }

    @Test   //Testing getting user by it's username
    public void getUserByUsername() throws Exception {

        when(entityDao.getEntityByNaturalId(any(User.class), anyString())).thenReturn(user);
        User extractedUser = userService.getUserByUsername("Faggot");
        assertEquals(extractedUser, user);
    }

}