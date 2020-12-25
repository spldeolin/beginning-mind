package com.spldeolin.beginningmind.extension.handle.impl;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.extension.filter.RequestTrackFilter;
import com.spldeolin.beginningmind.extension.handle.MdcHandle;

/**
 * @author Deolin 2020-12-25
 */
@Component
public class DefaultMdcHandle implements MdcHandle {

    private static final String handlerUrlPlaceholder = "url";

    @Override
    public void putInsignia(String insignia) {
        MDC.put(RequestTrackFilter.INSIGNIA_PLACEHOLDER, " [" + insignia + "]");
    }

    @Override
    public void putOtherMdcs(RequestTrack track) {
        MDC.put(handlerUrlPlaceholder, " [" + track.getRawRequest().getRequestURI() + "]");
    }

    @Override
    public void removeAllMdcs() {
        MDC.clear();
    }

}