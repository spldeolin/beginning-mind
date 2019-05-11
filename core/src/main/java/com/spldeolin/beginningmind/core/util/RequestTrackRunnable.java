package com.spldeolin.beginningmind.core.util;


import org.apache.logging.log4j.ThreadContext;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrackDTO;

/**
 * Runnable的装饰器
 *
 * <pre>
 * 这个类持有的Runnable对象，
 * 当Web请求线程实例化这个类时，
 * RequestTrackRunnable对象会记录下WebContext中的insignia，
 * 随后当RequestTrackRunnable对象线程启动时，insignia会保存到Log MDC
 * </pre>
 *
 * @author Deolin 2019-05-11
 */
public class RequestTrackRunnable implements Runnable {

    private final Runnable task;

    private final String insignia;

    private static final String LOG_MDC_INSIGNIA = "insignia";

    public RequestTrackRunnable(Runnable task) {
        this.task = task;

        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }
        insignia = track.getInsignia();
    }

    @Override
    public void run() {
        ThreadContext.put(LOG_MDC_INSIGNIA, "[" + insignia + "]");

        task.run();

        ThreadContext.remove(LOG_MDC_INSIGNIA);
    }

}
