package com.spldeolin.beginningmind.util;

import org.apache.shiro.SecurityUtils;
import com.spldeolin.beginningmind.security.dto.CurrentSigner;
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
        return (CurrentSigner) SecurityUtils.getSubject().getPrincipal();
    }

}
