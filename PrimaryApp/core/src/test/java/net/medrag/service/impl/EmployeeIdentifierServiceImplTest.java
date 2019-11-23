package net.medrag.service.impl;

import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.User;
import net.medrag.domain.enums.UserRole;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.MailService;
import net.medrag.service.dto.api.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeIdentifierServiceImplTest {

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserService<UserDto, User> userService;

    @Mock
    private MailService mailService;

    @Spy
    private EmployeeIdentifierServiceImpl service;

    @Before
    public void setUp() throws Exception {

        service = new EmployeeIdentifierServiceImpl();
        service.setEncoder(encoder);
        service.setMailService(mailService);
        service.setUserService(userService);

    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test   //Testing new password generating for existing user.
    public void generateNewPassword() throws Exception {

        User user = new User();
        user.setUsername("user");
        user.setEmail("example@example.com");

        doNothing().when(mailService).sendLoginPasswordEmail(anyString(), anyString(), anyString(), anyString());
        doNothing().when(userService).updateUser(any(User.class));
        when(userService.getUser(anyInt())).thenReturn(user);

        service.generateNewPassword(1);

        sleep(2500);

        verify(mailService,times(1)).sendLoginPasswordEmail(anyString(), anyString(), anyString(), anyString());
        verify(userService).updateUser(any(User.class));
        verify(userService).getUser(anyInt());
    }

    @Test   (expected = MedragServiceException.class)   //Testing exception throwing.
    public void generateNewPasswordThrowsException() throws Exception {

        when(userService.getUser(anyInt())).thenThrow(MedragServiceException.class);
        service.generateNewPassword(1);
    }

    @Test   //Testing adding new employee.
    public void identifyEmployee() throws Exception {

        UserDto user = new UserDto();
        user.setUsername("user");
        user.setRole("ROLE_WAREHOUSEMAN");
        user.setEmail("example@example.com");

        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(encoder.encode(anyString())).thenReturn("password");
        doNothing().when(mailService).sendLoginPasswordEmail(anyString(), anyString(), anyString(), anyString());
        doNothing().when(userService).addNewUser(any(User.class));

        service.identifyEmployee(user);

        verify(encoder).encode(anyString());
        verify(mailService).sendLoginPasswordEmail(anyString(), anyString(), anyString(), anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(userService).addNewUser(any(User.class));
    }

    @Test   //Testing password generating.
    public void generatePassword() throws Exception {

        String password = service.generatePassword();
        assertTrue( password.length() == 8);
    }

}