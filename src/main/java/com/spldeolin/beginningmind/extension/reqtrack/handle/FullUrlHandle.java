package com.spldeolin.beginningmind.extension.reqtrack.handle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Deolin 2020-12-25
 */
public interface FullUrlHandle {

    String parseFullUrl(HttpServletRequest request);

}
