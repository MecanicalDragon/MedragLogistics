package net.medrag.model.service;

import net.medrag.model.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * MailService implementation
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

//    public void sendConfirmEmail(String email, String confirmCode) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        Properties properties = new Properties();
//        try {
//            URL url = getClass().getResource("/mail.properties");
//            Path path = Paths.get(url.toURI());
//            properties.load(new FileInputStream(path.toFile()));
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//            throw new MessagingException("Can not load server.properties.");
//        }
//        message.addRecipients(Message.RecipientType.TO, email);
//        message.setSubject("Please, confirm your registration in My Web App");
//        String link = String.format("http://%s:%s/register/confirm/%s",
//                properties.get("server.address"),
//                properties.get("server.http.port"),
//                confirmCode);
//        message.setText("To confirm your registration, please, follow the next link: \n" + link +
//                "\n(Or triple click on it, copy and paste in your browser's address form, if your mail provider hasn't recognized it)");
//        mailSender.send(message);
//    }

    @Override
    public void sendLoginPasswordEmail(String email, String username, String password, String type) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("Your Medrag Logistics account data");

        switch(type){
            case "new":
                type = newAccount;
                break;
            case "restore":
                type = restoreAccount;
                break;
            default: type = newAccount;
        }

        String text = String.format(type +
                "Your login: %s \n" +
                "Your password: %s \n" +
                "You have no need to answer this email.", username, password);
        message.setText(text);
        mailSender.send(message);
    }
}
