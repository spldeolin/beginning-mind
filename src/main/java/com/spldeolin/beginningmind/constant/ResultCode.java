package com.spldeolin.beginningmind.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 控制器通用返回值的结果码
 * <pre>
 *   200 请求成功，返回预想结果
 *   400 请求方法交互失败
 *   401 没有登录
 *   403 登录者没有权限
 *   404 404 Not Found
 *   500 捕获到未考虑到的异常
 *  1001 存在业务错误，无法返回预想结果（用户名被占用、库存不足 等）
 *  </pre>
 *
 * @author Deolin
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功，预想的结果
    OK(200, ""),

    // 前后端交互错误
    BAD_REQEUST(400, "交互错误"),

    // 会话中没有登录者的信息
    UNSIGNED(401, "未登录或登录超时"),

    // 会话中没有该请求需要的权限
    FORBIDDEN(403, "权限不足"),

    // 404 Not Found
    NOT_FOUND(404, "Notfound"),

    // 捕获到未考虑到的异常
    INTERNAL_ERROR(500, "内部错误"),

    // 用户名被占用、库存不足 等
    SERVICE_ERROR(1001, "业务异常");

    private Integer code;

    private String defaultMessage;

}