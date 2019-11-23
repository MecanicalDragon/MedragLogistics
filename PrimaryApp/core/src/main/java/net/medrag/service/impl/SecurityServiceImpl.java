package net.medrag.service.impl;

import net.medrag.service.api.SecurityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Security service. Works with SecurityContextHolder.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    /**
     * Getting role of signed in user.
     *
     * @return - stringified role.
     */
    @Override
    public String getRoleOfSignedInUser() {
        List<? extends GrantedAuthority> authorities = (List)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.get(0).getAuthority();
    }

    /**
     * Getting username of signed in user.
     *
     * @return - username of signed in user.
     */
    @Override
    public String getUsernameOfSignedInUser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

}
