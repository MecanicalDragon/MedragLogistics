package net.medrag.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceImplTest {

    private SecurityServiceImpl securityService;

    @Mock
    private SecurityContext context;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @Mock
    private List authorities;

    @Mock
    private GrantedAuthority authority;


    @Before
    public void setUp() throws Exception {
        SecurityContextHolder.setContext(context);
        when(context.getAuthentication()).thenReturn(authentication);
        securityService = new SecurityServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        securityService = null;
    }

    @Test
    public void getRoleOfSignedInUser() throws Exception {

        when(authentication.getAuthorities()).thenReturn(authorities);
        when(authorities.get(anyInt())).thenReturn(authority);
        when(authority.getAuthority()).thenReturn("ROLE_MUTHERFAKER");
        String role = securityService.getRoleOfSignedInUser();
        assertEquals(role, "ROLE_MUTHERFAKER");
    }

    @Test
    public void getUsernameOfSignedInUser() throws Exception {

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn("username");
        String username = securityService.getUsernameOfSignedInUser();
        assertEquals(username, "username");
    }

}