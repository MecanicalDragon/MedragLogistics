package net.medrag.service.impl;

import net.medrag.dao.MedragRepositoryException;
import net.medrag.dao.api.UserDao;
import net.medrag.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link UserDetails} for this app.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class UserDetailsServiceImpl implements UserDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverHandlerServiceImpl.class);

    private UserDao<User> userDao;

    @Autowired
    public void setUserDao(UserDao<User> userDao) {
        this.userDao = userDao;
    }

    /**
     * Signing in user in app.
     *
     * @param username - username
     * @return - Spring security user.
     * @throws UsernameNotFoundException - we have avoided it.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userDao.getEntityByNaturalId(new User(), username);
        } catch (MedragRepositoryException e) {
            LOGGER.error("Could not load user with username {}. {}", username, e);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
