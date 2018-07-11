package net.medrag.model.service;

import javax.mail.MessagingException;

/**
 * Interface for MailService
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface MailService {
    void sendLoginPasswordEmail(String email, String username, String password, String type) throws MessagingException;
}
