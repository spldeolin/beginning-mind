package com.spldeolin.beginningmind.util;

/**
 * 工具类：简化向HttpSession对象存取attribute的代码量
 *
 * 这个方法必须在Web请求所在的线程调用，否则抛出异常
 *
 * @author Deolin
 */
public class SessionUtils {

    private SessionUtils() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    /**
     * 将k-v存入Session，只要Session不失效，该k-v就不会失效
     */
    public static void set(String key, Object value) {
        WebContext.getSession().setAttribute(key, value);
    }

    /**
     * 获取v，如果该k-v已经失效，返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) WebContext.getSession().getAttribute(key);
    }

    /**
     * 移除k-v
     */
    public static void remove(String key) {
        WebContext.getSession().removeAttribute(key);
    }

}
