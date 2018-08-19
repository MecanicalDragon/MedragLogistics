package net.medrag.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceImplTest {

    private SecurityServiceImpl securityService;

    @Mock
    private SecurityContextHolder contextHolder;

    @Mock
    private SecurityContext context;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @Test (expected = NullPointerException.class)
    public void getRoleOfSignedInUser() throws Exception {
        securityService = new SecurityServiceImpl();
        String role = securityService.getRoleOfSignedInUser();
    }

    @Test (expected = NullPointerException.class)
    public void getUsernameOfSignedInUser() throws Exception {

//        when(SecurityContextHolder.getContext()).thenReturn(context);
//        when(context.getAuthentication()).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(user);
//        when(user.getUsername()).thenReturn("username");

        securityService = new SecurityServiceImpl();
        String username = securityService.getUsernameOfSignedInUser();
        assertEquals(username, "username");
    }

}