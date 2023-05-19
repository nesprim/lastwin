package com.gsardina.lastwin.utils;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtils {

    @Value("${lastwin.mail.host}")
    private String mailHost;

    @Value("${lastwin.mail.port}")
    private String mailPort;

    @Value("${lastwin.mail.user}")
    private String mailUser;

    @Value("${lastwin.mail.password}")
    private String mailPassword;

    @Value("${lastwin.baseURL}")
    private String baseURL;

    private final Properties mailProperties = new Properties();

    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @PostConstruct
    public void initProperties() {
        mailProperties.put("mail.smtp.host", mailHost);
        mailProperties.put("mail.smtp.port", mailPort);
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
    }

    public static String generateConfirmCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    public void sendRegistrationMail(String username, String email, String confirmCode) {
        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser, mailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(MessageUtils.CONFIRM_SIGNUP);

            String body = MessageUtils.MAIL_BODY_CONFIRM_SIGNUP;

            body = body.replace("<username>", username);
            body = body.replace("<code>", confirmCode);

            message.setText(body);

            Transport.send(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
