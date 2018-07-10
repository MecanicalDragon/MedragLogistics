package net.medrag.model.service;

import net.medrag.dto.DriverDto;
import net.medrag.dto.Dto;
import net.medrag.dto.UserDto;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.User;
import net.medrag.model.domain.enums.UserRole;
import org.jetbrains.annotations.NotNull;
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
public class EmployeeIdentifierServiceImpl implements EmployeeIdentifierService {

    private UserService<UserDto, User> userService;

    private DriverService<DriverDto, Driver>driverService;

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

    private static final String warehousePrefix = "WHM-";
    private static final String resourcePrefix = "RSM-";
    private static final String logisticPrefix = "MGR-";

    @Override
    @Transactional
    public void identifyEmployee(Dto dto) {

        if (dto instanceof DriverDto) {
            User driver = new User();
            driver.setUsername(((DriverDto) dto).getPersonalNumber());
            driver.setEmail(((DriverDto) dto).getEmail());
            driver.setRole(UserRole.ROLE_DRIVER);
            String password = generatePassword(driver);

            userService.addNewUser(driver);
            try {
                mailService.sendLoginPasswordEmail(driver.getEmail(), driver.getUsername(), password);
            } catch (MessagingException e) {
                logger.error("Could not send email to the mail-address {}", driver.getEmail());
            }
        }

        if (dto instanceof UserDto) {
            int number = new Random().nextInt(89999) + 10000;
            String prefix = null;

            switch (((UserDto) dto).getRole()) {
                case "ROLE_MANAGER":
                    prefix = logisticPrefix;
                    break;
                case "ROLE_WAREHOUSEMAN":
                    prefix = warehousePrefix;
                    break;
                case "ROLE_RESOURCE":
                    prefix = resourcePrefix;
                    break;
                default:
                    prefix = null;
            }

            String username = prefix + number;

            ((UserDto) dto).setUsername(username);
        }


    }

    @NotNull
    private String generatePassword(User user) {
        StringBuilder passwordBuilder = new StringBuilder();
        do {
            passwordBuilder.append(Integer.toHexString(new Random().nextInt(Integer.MAX_VALUE)));
        } while (passwordBuilder.toString().length() < 16);

        String password = passwordBuilder.toString().substring(0,8);
        user.setPassword(encoder.encode(password));
        return password;
    }

    @Override
    public void generateNewPassword(Integer id) {
        User user = userService.getUser(id);
        String password = generatePassword(user);
        try {
            mailService.sendLoginPasswordEmail(user.getEmail(), user.getUsername(), password);
        } catch (MessagingException e) {
            logger.error("Could not send email to the mail-address {}", user.getEmail());
        }

    }

}
