package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.CoreProperties.EmailProp;
import com.spldeolin.beginningmind.core.service.EmailService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */
@Log4j2
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
