package com.spldeolin.beginningmind.extension.handle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Deolin 2020-12-25
 */
public interface RequestIgnoreHandle {

    boolean isIgnore(HttpServletRequest request);

}
