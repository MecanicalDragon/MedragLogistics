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
    void sendLoginPasswordEmail(String email, String username, String password, String type) throws MessagingException;
    void sendDeliveredCargoEmail (CargoDto cargo) throws MessagingException;
    void sendTakenOrderMail(OrderrDto order) throws MessagingException ;
    void sendCompiledRouteMesaage(DriverDto driver, String destination) throws MessagingException;
    void sendWorkedTimeLimitMail(DriverDto driver) throws MessagingException;
}
