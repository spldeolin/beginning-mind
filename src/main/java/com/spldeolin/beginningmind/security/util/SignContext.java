package com.spldeolin.beginningmind.security.util;


import javax.servlet.http.HttpServletRequest;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.security.dto.CurrentSignerDTO;
import com.spldeolin.beginningmind.serviceimpl.SignServiceImpl;
import com.spldeolin.beginningmind.util.ServletUtil;

/**
 * 工具类：登录者上下文
 *
 * @author Deolin
 */
public class SignContext {

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
        HttpServletRequest currentHttpRequest = ServletUtil.getCurrentHttpRequest();
        if (currentHttpRequest == null) {
            return null;
        }
        return (CurrentSignerDTO) currentHttpRequest.getSession().getAttribute(SignServiceImpl.SIGNER_SESSION_KEY);
    }

    /**
     * @return 当前登录者用户
     */
    public static UserEntity user() {
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
        UserEntity user = user();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    /**
     * @return 当前登录者用户名称
     */
    public static String userName() {
        UserEntity user = user();
        if (user == null) {
            return null;
        }
        return user.getName();
    }

}
