package com.spldeolin.beginningmind.core.util;

import org.apache.shiro.SecurityUtils;
import com.spldeolin.beginningmind.core.security.dto.CurrentSigner;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import lombok.experimental.UtilityClass;

/**
 * 工具类：登录者
 *
 * @author Deolin
 */
@UtilityClass
public class Signer {

    /**
     * @return 当前登录者
     */
    public static CurrentSigner current() {
        CurrentSigner currentSigner = (CurrentSigner) SecurityUtils.getSubject().getPrincipal();
        if (currentSigner == null) {
            throw new UnsignedException("未登录或登录超时");
        }
        return currentSigner;
    }

}
