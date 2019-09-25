package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.CoreProperties;
import com.spldeolin.beginningmind.CoreProperties.EmailProp;
import com.spldeolin.beginningmind.service.EmailService;

/**
 * @author Deolin 2018/11/16
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public void sendEmail(List<String> emails, String subject, String content) {
        EmailProp prop = coreProperties.getEmail();
        EmailPopulatingBuilder builder = EmailBuilder.startingBlank()
                .from(prop.getAddresserName(), prop.getAddresserEmail())
                .withSubject(subject)
                .withPlainText(content);
        for (String email : emails) {
            builder.to(email.split("@")[0], email);
        }
        MailerBuilder.withSMTPServer(
                prop.getServerHost(),
                prop.getServerPort(),
                prop.getAddresserEmail(),
                prop.getAddresserAuthCode())
                .buildMailer()
                .sendMail(builder.buildEmail());
    }

}
