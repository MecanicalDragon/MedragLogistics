package net.medrag.service.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.service.api.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * MailService - unbelievable, but it sales emails.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;

    private static final String newAccount = "Congratulations! Now you're member of our company! There are your authentication data for entering our web application\n";
    private static final String restoreAccount = "We have restored your authentication data. Now you have new login and password. Don't loose it again!\n";

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Email with login and password to a new employee or already existing.
     *
     * @param email    - email
     * @param username - username
     * @param password - password
     * @param type     - type of email: with new authorities of restored.
     * @throws MessagingException - true
     */
    @Override
    public void sendLoginPasswordEmail(String email, String username, String password, String type) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("Your Medrag Logistics account data");

        switch (type) {
            case "new":
                type = newAccount;
                break;
            case "restore":
                type = restoreAccount;
                break;
            default:
                type = newAccount;
        }

        String text = String.format(type +
                "Your login: %s \n" +
                "Your password: %s \n" +
                "You have no need to answer this email.", username, password);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Message to customer about delivering his cargo.
     *
     * @param cargo - delivered cargo
     * @throws MessagingException - it happens
     */
    @Override
    public void sendDeliveredCargoEmail(CargoDto cargo) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, cargo.getOwner().getEmail());
        message.setSubject("Your cargo has been delivered!");

        String text = String.format("Dear %s %s! Your cargo %s with index %s (order number %s) nas been delivered in city %s! " +
                        "Come and get it. \n (You can watch your order complete status, following the link below)\n \n" +
                        "http://localhost:8080/orderInfo/%s \n \n" +
                        "Your MedragLogistics.", cargo.getOwner().getName(),
                cargo.getOwner().getSurname(), cargo.getName(), cargo.getIndex(), cargo.getOrderr().getIndex(),
                cargo.getDestinationName(), cargo.getOrderr().getIndex());
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Email to customer with order information and link to order status page.
     *
     * @param order customer's order
     * @throws MessagingException - sometimes
     */
    @Override
    public void sendTakenOrderMail(OrderrDto order) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, order.getOwner().getEmail());
        message.setSubject("Your order has been taken to handling.");

        StringBuilder cargoList = new StringBuilder("This is your cargoes list: \n\n");
        for (CargoDto cargo : order.getCargoes()) {
            cargoList.append("Cargo: ").append(cargo.getName()).append("\n")
                    .append("Index: ").append(cargo.getIndex()).append("\n")
                    .append("Departure: ").append(cargo.getDepartureName()).append("\n")
                    .append("Destination: ").append(cargo.getDestinationName()).append("\n\n");
        }

        String text = String.format("Dear %s %s! Your order, registered by the index %s nas been taken to handling. \n" +
                        "%s You can watch it's delivery status, following the next link: \n" +
                        "http://localhost:8080/orderInfo/%s", order.getOwner().getName(),
                order.getOwner().getSurname(), order.getIndex(), cargoList.toString(), order.getIndex());
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Route assigning mail to driver.
     *
     * @param driver - assigned driver.
     * @param destination - Destination city name.
     * @throws MessagingException - we ignore it.
     */
    @Override
    public void sendCompiledRouteMesaage(DriverDto driver, String destination) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, driver.getEmail());
        message.setSubject("You assigned to the route.");

        String text = String.format("%s %s, you assigned to the route to the city %s. Take your workplace on the truck %s.",
                driver.getName(), driver.getSurname(), destination, driver.getCurrentTruck().getRegNumber());
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * When driver reaches his worked time limit.
     *
     * @param driver - overworked driver.
     * @throws MessagingException - noway.
     */
    @Override
    public void sendWorkedTimeLimitMail(DriverDto driver) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, driver.getEmail());
        message.setSubject("You have reached time limit!");

        String text = String.format("%s %s, you have reached the limit of worked time this month. " +
                        "Your working status has been set to 'resting', and now you cannot be assigned to any routes. " +
                        "Your time is paid no more. Have a nice rest till the next month.",
                driver.getName(), driver.getSurname());
        message.setText(text);
        mailSender.send(message);
    }
}
