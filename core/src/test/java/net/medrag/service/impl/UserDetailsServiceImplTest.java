package net.medrag.service.impl;

import net.medrag.dao.api.UserDao;
import net.medrag.domain.entity.User;
import net.medrag.domain.enums.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserDao<User> userDao;

    @Test
    public void loadUserByUsername() throws Exception {

        User user = new User();
        user.setUsername("Vasiliy");
        user.setId(1);
        user.setRole(UserRole.ROLE_WAREHOUSEMAN);
        user.setPassword("password");

        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
        userDetailsService.setUserDao(userDao);
        when(userDao.getEntityByNaturalId(any(User.class), anyString())).thenReturn(user);

        org.springframework.security.core.userdetails.User springUser =
                (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername("Vasiliy");

        assertEquals(user.getUsername(), springUser.getUsername());

    }
}