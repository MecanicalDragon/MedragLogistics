package net.medrag.service.impl;

import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.User;
import net.medrag.domain.enums.UserRole;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.EmployeeIdentifierService;
import net.medrag.service.api.MailService;
import net.medrag.service.dto.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.security.SecureRandom;

/**
 * This bulky class is needed for handling requests to the database, related with adding and editing users
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class EmployeeIdentifierServiceImpl implements EmployeeIdentifierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeIdentifierServiceImpl.class);

    private UserService<UserDto, User> userService;

    private MailService mailService;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setUserService(UserService<UserDto, User> userService) {
        this.userService = userService;
    }

    private static final String warehousePrefix = "WHM-";
    private static final String resourcePrefix = "RSM-";
    private static final String logisticPrefix = "MGR-";

    /**
     * Method generates new password for user.
     *
     * @param id - user id.
     * @throws MedragServiceException - if there is no database, connected to app.
     */
    @Override
    @Transactional
    public void generateNewPassword(Integer id) throws MedragServiceException {
        User user = userService.getUser(id);
        String password = generatePassword();
        user.setPassword(encoder.encode(password));
        userService.updateUser(user);
        new Thread(() -> {
            try {
                mailService.sendLoginPasswordEmail(user.getEmail(), user.getUsername(), password, "restore");
            } catch (MessagingException e) {
                LOGGER.error("Email with new passwodr hasn't been sent to user {}. {}", user.getUsername(), e);
            }
        }).start();
    }

    /**
     * Adding new Employee to the database.
     *
     * @param user - new user.
     * @throws MedragServiceException - we don't employ bad people.
     */
    @Override
    @Transactional
    public void identifyEmployee(UserDto user) throws MedragServiceException {

        String prefix;
        switch (user.getRole()) {
            case "ROLE_WAREHOUSEMAN":
                prefix = warehousePrefix;
                break;
            case "ROLE_RESOURCE":
                prefix = resourcePrefix;
                break;
            default:
                prefix = logisticPrefix;
        }

        do {
            int random = new SecureRandom().nextInt(89999) + 10000;
            user.setUsername(prefix + random);
        }
        while (userService.getUserByUsername(user.getUsername()) != null);

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setRole(UserRole.valueOf(user.getRole()));
        String password = generatePassword();
        newUser.setPassword(encoder.encode(password));

        try {
            mailService.sendLoginPasswordEmail(newUser.getEmail(), newUser.getUsername(), password, "new");
            userService.addNewUser(newUser);
        } catch (MessagingException e) {
            throw new MedragServiceException(e);
        }

    }

    /**
     * Just generate new password.
     *
     * @return - new random password.
     */
    @Override
    public String generatePassword() {
        StringBuilder passwordBuilder = new StringBuilder();
        do {
            passwordBuilder.append(Integer.toHexString(new SecureRandom().nextInt(Integer.MAX_VALUE)));
        } while (passwordBuilder.toString().length() < 16);

        return passwordBuilder.toString().substring(0, 8);
    }

}
