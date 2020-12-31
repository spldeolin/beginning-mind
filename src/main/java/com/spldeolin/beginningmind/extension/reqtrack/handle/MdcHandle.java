package com.spldeolin.beginningmind.extension.reqtrack.handle;

import com.spldeolin.beginningmind.extension.reqtrack.RequestTrack;

/**
 * @author Deolin 2020-12-25
 */
public interface MdcHandle {

    void putInsignia(String value);

    void putOtherMdcs(RequestTrack track);

    void removeAllMdcs();

}
