package com.spldeolin.beginningmind.extension.reqtrack.handle;

import com.spldeolin.beginningmind.extension.reqtrack.RequestTrack;

/**
 * 操作Log MDC
 *
 * @author Deolin 2020-12-25
 */
public interface MdcHandle {

    /**
     * 将insignia存入MDC
     */
    void putInsignia(String value);

    /**
     * 将其他信息存入MDC
     */
    void putOtherMdcs(RequestTrack track);

    /**
     * 移除MDC中所有信息
     */
    void removeAllMdcs();

}
