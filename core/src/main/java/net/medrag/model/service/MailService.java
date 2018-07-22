package net.medrag.model.service;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.OrderrDto;

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
    void sendCompiledRouteMesaage(DriverDto driver, CityDto destination) throws MessagingException;
}
