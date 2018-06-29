package net.medrag.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sun.security.krb5.Credentials;

import java.util.Collection;
import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String getRoleOfSignedInUser() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            return authority.getAuthority();
        }
        return null;
    }

    //  for Validator

    @Override
    public String getUsernameOfSignedInUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

//    @Override
//    public void autoLogin(String username, String password) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken
//                (userDetails, password, userDetails.getAuthorities());
//        authenticationManager.authenticate(upaToken);
//        if (upaToken.isAuthenticated()){
//            SecurityContextHolder.getContext().setAuthentication(upaToken);
//        }
//    }
}
