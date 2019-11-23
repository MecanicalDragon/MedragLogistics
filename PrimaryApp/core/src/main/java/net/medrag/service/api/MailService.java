package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.OrderrDto;

import javax.mail.MessagingException;

/**
 * Interface for MailService
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface MailService {

    /**
     * Email with login and password to a new employee or already existing.
     *
     * @param email    - email
     * @param username - username
     * @param password - password
     * @param type     - type of email: with new authorities of restored.
     * @throws MessagingException - true
     */
    void sendLoginPasswordEmail(String email, String username, String password, String type) throws MessagingException;

    /**
     * Message to customer about delivering his cargo.
     *
     * @param cargo - delivered cargo
     * @throws MessagingException - it happens
     */
    void sendDeliveredCargoEmail (CargoDto cargo) throws MessagingException;

    /**
     * Email to customer with order information and link to order status page.
     *
     * @param order customer's order
     * @throws MessagingException - sometimes
     */
    void sendTakenOrderMail(OrderrDto order) throws MessagingException ;

    /**
     * Route assigning mail to driver.
     *
     * @param driver - assigned driver.
     * @param destination - Destination city name.
     * @throws MessagingException - we ignore it.
     */
    void sendCompiledRouteMesaage(DriverDto driver, String destination) throws MessagingException;

    /**
     * When driver reaches his worked time limit.
     *
     * @param driver - overworked driver.
     * @throws MessagingException - noway.
     */
    void sendWorkedTimeLimitMail(DriverDto driver) throws MessagingException;
}
