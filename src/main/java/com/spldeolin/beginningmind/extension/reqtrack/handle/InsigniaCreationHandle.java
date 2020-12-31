package com.spldeolin.beginningmind.extension.reqtrack.handle;

import javax.servlet.http.HttpServletRequest;

/**
 * insignia的生成方式
 *
 * @author Deolin 2020-12-25
 */
public interface InsigniaCreationHandle {

    String createInsignia(HttpServletRequest request);

}
