package com.spldeolin.beginningmind.core.service;

import java.util.List;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.CoreProperties;

/**
 * E-Mail
 *
 * @author Deolin
 */
@Service
public class EmailService {

    @Autowired
    private CoreProperties coreProperties;

    /**
     * 发送邮件
     *
     * @param emails 收件人E-mail，支持群发
     * @param subject 邮件主题
     * @param content 邮件正文
     */
    public void sendEmail(List<String> emails, String subject, String content) {
        CoreProperties.Email prop = coreProperties.getEmail();
        EmailPopulatingBuilder builder = EmailBuilder.startingBlank()
                .from(prop.getAddresserName(), prop.getAddresserEmail())
                .withSubject(subject)
                .withPlainText(content);
        for (String email : emails) {
            builder.to(email.split("@")[0], email);
        }
        MailerBuilder
                .withSMTPServer(
                        prop.getServerHost(),
                        prop.getServerPort(),
                        prop.getAddresserEmail(),
                        prop.getAddresserAuthCode())
                .buildMailer()
                .sendMail(builder.buildEmail());
    }


}
