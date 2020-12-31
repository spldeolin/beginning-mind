package com.spldeolin.beginningmind.extension.reqtrack.handle;

import javax.servlet.http.HttpServletRequest;

/**
 * 从Request中转化出完整的URL
 *
 * @author Deolin 2020-12-25
 */
public interface FullUrlHandle {

    String parseFullUrl(HttpServletRequest request);

}
