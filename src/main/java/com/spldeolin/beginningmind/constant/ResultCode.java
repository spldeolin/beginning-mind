package com.spldeolin.beginningmind.constant;

public enum ResultCode {

    OK(200, null),
    BAD_REQEUST(400, "交互错误"),
    UNAUTHORIZED(401, "未登录或登录超时"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "资源不存在或是已被删除"),
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