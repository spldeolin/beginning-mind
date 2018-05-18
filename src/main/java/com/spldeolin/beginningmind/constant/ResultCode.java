package com.spldeolin.beginningmind.constant;

/**
 * 控制器通用返回值的结果码
 * <pre>
 *   200 请求成功，返回预想结果
 *   400 请求方法交互失败
 *   401 没有认证
 *   403 登录者没有权限
 *   404 HTTP404
 *   500 内部BUG
 *  1001 存在业务错误，无法返回预想结果（商品库存不足等）
 *  </pre>
 *
 * @author Deolin
 */
public enum ResultCode {

    OK(200, null),
    BAD_REQEUST(400, "交互错误"),
    UNSIGNED(401, "未登录或登录超时"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "请求或资源不存在"),
    INTERNAL_ERROR(500, "内部错误"),
    SERVICE_ERROR(1001, "业务异常");

    private Integer code;

    private String defaultMessage;

    ResultCode(Integer code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        return "ResultCode{" + "code=" + code + ", defaultMessage='" + defaultMessage + '\'' + '}';
    }

}