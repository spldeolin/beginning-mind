package com.spldeolin.beginningmind.security;

import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.security.exception.UnsignedException;
import com.spldeolin.beginningmind.security.util.SignContext;

/**
 * @author Deolin 2018/12/02
 */
@Component
public class SignedChecker {

    public void ensureSigned() throws UnsignedException {
        if (!SignContext.isSigning()) {
            throw new UnsignedException("未登录或登录超时");
        }
    }

}
