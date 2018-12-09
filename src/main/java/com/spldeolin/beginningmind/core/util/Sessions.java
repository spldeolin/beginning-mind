package com.spldeolin.beginningmind.core.util;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import lombok.Data;
import lombok.experimental.UtilityClass;

/**
 * 工具类：简化HttpSession存取Attribute的代码量，并通过内部实现，让调用方可以为每个Attribute指定失效时间
 *
 * @author Deolin
 */
@UtilityClass
public class Sessions {

    private static HttpSession session() {
        return WebContext.getSession();
    }

    /**
     * 将k-v存入Session，只要Session不失效，该k-v就不会失效
     */
    public static void set(String key, Object value) {
        set(key, value, SessionConfig.SESSION_EXPIRE_SECONDS);
    }

    /**
     * 将k-v存入Session，并指定该k-v的失效秒数
     */
    public static void set(String key, Object value, int attributeExpireSeconds) {
        ValueWrapper wrapper = ValueWrapper.wrap(value, attributeExpireSeconds);
        session().setAttribute(key, wrapper);
    }

    /**
     * 获取v，如果该k-v已经失效，返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        Object value = session().getAttribute(key);
        if (value instanceof ValueWrapper) {
            ValueWrapper wrapper = (ValueWrapper) value;
            if (isExpired(wrapper.getSetAt(), wrapper.getExpiredSeconds())) {
                remove(key);
                value = null;
            } else {
                value = wrapper.getValue();
            }
        }
        return (T) value;
    }

    /**
     * 移除k-v
     */
    public static void remove(String key) {
        session().removeAttribute(key);
    }

    private boolean isExpired(LocalDateTime setAt, int expireSeconds) {
        LocalDateTime expiredAt = setAt.plusSeconds(expireSeconds);
        return LocalDateTime.now().isAfter(expiredAt);
    }

    @Data
    public static class ValueWrapper implements Serializable {

        private Object value;

        private LocalDateTime setAt = LocalDateTime.now();

        private Integer expiredSeconds;

        private static final long serialVersionUID = 1L;

        private ValueWrapper() {
        }

        public static ValueWrapper wrap(Object value, Integer expiredSeconds) {
            ValueWrapper wrapper = new ValueWrapper();
            wrapper.setValue(value);
            wrapper.setExpiredSeconds(expiredSeconds);
            return wrapper;
        }

    }

}
