package com.spldeolin.beginningmind.core.service;

import java.util.List;

/**
 * @author Deolin 2018/11/16
 */
public interface EmailService {

    void sendEmail(List<String> emails, String subject, String content);

}
