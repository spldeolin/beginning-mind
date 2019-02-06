package com.spldeolin.beginningmind.core.service;

import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.dto.CaptchaVO;
import com.spldeolin.beginningmind.core.dto.SignerProfileVO;
import com.spldeolin.beginningmind.core.input.SignInput;

/**
 * 登录
 *
 * @author Deolin
 */
@Service
public interface SignService {

    /**
     * 生成验证码图片与用于获取验证码缓存的token
     */
    CaptchaVO captcha();

    /**
     * 登录
     */
    SignerProfileVO signIn(SignInput input);

    /**
     * 登出
     */
    void signOut();

}