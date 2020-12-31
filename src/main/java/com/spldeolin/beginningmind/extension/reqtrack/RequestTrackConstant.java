package com.spldeolin.beginningmind.extension.reqtrack;

/**
 * Request Track 机制中涉及到的常量
 *
 * @author Deolin 2020-12-25
 */
public interface RequestTrackConstant {

    /**
     * 其他所有Filter需要大于这个值，否则使用不了RequestTrack.CURRENT
     */
    int REQUEST_TRACK_FILTER_ORDER = 1001;

    String INSIGNIA_PLACEHOLDER = "insignia";

}
