package com.spldeolin.beginningmind.core.security.util;

import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.security.dto.CurrentSignerDTO;
import com.spldeolin.beginningmind.core.service.impl.SignServiceImpl;
import com.spldeolin.beginningmind.core.util.Sessions;
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
        return current() != null;
    }

    /**
     * @return 当前登录者
     */
    public static CurrentSignerDTO current() {
        return Sessions.get(SignServiceImpl.SIGNER_SESSION_KEY);
    }

    /**
     * @return 当前登录者用户
     */
    public static User user() {
        CurrentSignerDTO current = current();
        if (current == null) {
            return null;
        }
        return current.getUser();
    }

    /**
     * @return 当前登录者用户ID
     */
    public static Long userId() {
        User user = user();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    /**
     * @return 当前登录者用户名称
     */
    public static String userName() {
        User user = user();
        if (user == null) {
            return null;
        }
        return user.getName();
    }

}
