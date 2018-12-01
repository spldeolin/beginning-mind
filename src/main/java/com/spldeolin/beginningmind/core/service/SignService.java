package com.spldeolin.beginningmind.core.service;

import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.dto.SignerProfileDTO;
import com.spldeolin.beginningmind.core.input.SignInput;

/**
 * @author Deolin
 */
@Service
public interface SignService {

    String captcha();

    /**
     * 登录
     */
    SignerProfileDTO signIn(SignInput input);

    /**
     * 登出
     */
    void signOut();

    /**
     * 指定用户是否登录中
     */
    Boolean isSigning(Long userId);

    /**
     * 将指定用户踢下线
     */
    void kill(Long userId);

}