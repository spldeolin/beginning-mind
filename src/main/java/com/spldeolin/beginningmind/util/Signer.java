package com.spldeolin.beginningmind.util;

import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;
import com.spldeolin.beginningmind.constant.CoupledConstant;
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

    /**
     * @return 本分布式节点下，当前登录者是否已经设置MDC
     */
    public static boolean existMDC() {
        return ThreadContext.get(CoupledConstant.LOG_PATTERN_PARAM) != null;
    }

    /**
     * 将当前登录者的username设置到日志MDC
     */
    public static void mdc() {
        ThreadContext.put(CoupledConstant.LOG_PATTERN_PARAM, "[" + current().getSecurityAccount().getUsername() + "]");
    }

    /**
     * 清除日志MDC，退出登录时调用
     */
    public static void removeMDC() {
        ThreadContext.remove(CoupledConstant.LOG_PATTERN_PARAM);
    }

}
