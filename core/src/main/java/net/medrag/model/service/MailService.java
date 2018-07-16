package net.medrag.model.service;

import net.medrag.model.dto.CargoDto;

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
}
