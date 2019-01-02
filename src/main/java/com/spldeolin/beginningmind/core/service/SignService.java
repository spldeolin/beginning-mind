package com.spldeolin.beginningmind.core.service;

import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.dto.SignerProfileVO;
import com.spldeolin.beginningmind.core.input.SignInput;

/**
 * 登录
 *
 * @author Deolin
 */
@Service
public interface SignService {

    String captcha();

    /**
     * 登录
     */
    SignerProfileVO signIn(SignInput input);

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