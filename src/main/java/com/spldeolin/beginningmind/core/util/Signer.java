package com.spldeolin.beginningmind.core.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.security.dto.CurrentSignerDTO;
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
     * @return 当前会话是否登录中
     */
    public static boolean isSigning() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated() || subject.isRemembered();
    }

    /**
     * @return 当前登录者
     */
    public static CurrentSignerDTO current() {
        CurrentSignerDTO currentSigner = (CurrentSignerDTO) SecurityUtils.getSubject().getPrincipal();
        if (currentSigner == null) {
            throw new UnsignedException("未登录或登录超时");
        }
        return currentSigner;
    }

    /**
     * @return 当前登录者用户
     */
    public static User user() {
        return current().getUser();
    }

    /**
     * @return 当前登录者用户ID
     */
    public static Long userId() {
        return user().getId();
    }

    /**
     * @return 当前登录者用户名称
     */
    public static String userName() {
        return user().getName();
    }

}
