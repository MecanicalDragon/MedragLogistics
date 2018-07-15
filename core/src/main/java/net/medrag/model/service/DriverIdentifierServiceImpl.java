package net.medrag.model.service;

import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.UserDto;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.User;
import net.medrag.model.domain.enums.UserRole;
import net.medrag.model.service.dto.DTOServiceImpl;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Random;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DriverIdentifierServiceImpl implements DriverIdentifierService {

    private UserService<UserDto, User> userService;

    private DriverService<DriverDto, Driver> driverService;

    private MailService mailService;

    private BCryptPasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(DTOServiceImpl.class);

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

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

    /**
     * method for identifying new driver as a user. It creates and adds into the database new user with driver
     * authorities and the same name.
     *
     * @param driverDto - inputted driverDto
     */
    @Override
    @Transactional
    public void identifyNewDriver(DriverDto driverDto) {

        User driverUser = new User();
        driverUser.setUsername(driverDto.getPersonalNumber());
        driverUser.setEmail(driverDto.getEmail());
        driverUser.setRole(UserRole.ROLE_DRIVER);

        StringBuilder passwordBuilder = new StringBuilder();
        do {
            passwordBuilder.append(Integer.toHexString(new Random().nextInt(Integer.MAX_VALUE)));
        } while (passwordBuilder.toString().length() < 16);
        String password = passwordBuilder.toString().substring(0, 8);

        driverUser.setPassword(encoder.encode(password));
        try {
            mailService.sendLoginPasswordEmail(driverUser.getEmail(), driverUser.getUsername(), password, "new");
            driverService.addDto(driverDto, new Driver());
            userService.addNewUser(driverUser);
        } catch (MessagingException e) {
            logger.error("Could not send email to the mail-address {}", driverUser.getEmail());
        }

    }

    @Override
    @Transactional
    public void updateDriver(DriverDto driver) {
        User user = userService.getUserByUsername(driver.getPersonalNumber());
        if (user != null) {
            user.setEmail(driver.getEmail());
            userService.updateUser(user);
        }
        driverService.updateDtoStatus(driver, new Driver());
    }

    @Override
    @Transactional
    public void removeDriver(DriverDto removableDriver) {
        driverService.removeDto(removableDriver, new Driver());
        User user = userService.getUserByUsername(removableDriver.getPersonalNumber());
        if (user != null) {
            userService.deleteUser(user);
        }
    }


}
