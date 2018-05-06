package com.spldeolin.beginningmind.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.constant.CoupledConstant;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.util.RequestContextUtils;

@RestController
public class RedirectController implements ErrorController {

    @GetMapping("unauthc")
    public RequestResult unauthc() {
        return RequestResult.failure(ResultCode.UNAUTHENTICATED);
    }

    @RequestMapping(CoupledConstant.ERROR_PAGE_URL)
    public RequestResult notFound() {
        HttpServletRequest request = RequestContextUtils.request();
        Integer status = (Integer) request.getAttribute("{HTTP_STATUS_CODE}");
        // 浏览器直接访问这个请求时，直接返回
        if (status == null) {
            return RequestResult.failure(ResultCode.NOT_FOUND);
        }
        // 特殊对待HTTP404以外的错误
        if (!status.equals(404)) {
            return RequestResult.failure(ResultCode.INTERNAL_ERROR, "FRAMEWORK ERROR!");
        }
        return RequestResult.failure(ResultCode.NOT_FOUND,
                "请求不存在 [" + request.getAttribute("{HTTP_METHOD}") + "]" + request.getAttribute("{HTTP_URL}"));
    }

    @Override
    public String getErrorPath() {
        return CoupledConstant.ERROR_PAGE_URL;
    }

}
