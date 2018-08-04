package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/8/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "request_track")
public class RequestTrack implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 审计字段 插入时间
     */
    @Column(name = "inserted_at")
    @JsonIgnore
    private LocalDateTime insertedAt;

    /**
     * 审计字段 更新时间
     */
    @Version
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 审计字段 删除标记（-1代表未被删除，其他代表被删除）
     */
    @Column(name = "deletion_flag")
    @JsonIgnore
    private Long deletionFlag;

    /**
     * 请求标识
     */
    private String insignia;

    /**
     * 请求时间
     */
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    /**
     * URL
     */
    private String url;

    /**
     * 请求动词
     */
    @Column(name = "http_method")
    private String httpMethod;

    /**
     * 控制器
     */
    private String controller;

    /**
     * 请求方法
     */
    @Column(name = "request_method")
    private String requestMethod;

    /**
     * 请求耗时
     */
    @Column(name = "processing_milliseconds")
    private Long processingMilliseconds;

    /**
     * 请求体
     */
    @Column(name = "request_body")
    private String requestBody;

    /**
     * 返回值
     */
    @Column(name = "response_body")
    private String responseBody;

    /**
     * 登录者用户ID
     */
    @Column(name = "signed_user_id")
    private Long signedUserId;

    /**
     * 登录者用户名
     */
    @Column(name = "signed_user_name")
    private String signedUserName;

    /**
     * 登录者用户手机
     */
    @Column(name = "signed_user_mobile")
    private String signedUserMobile;

    /**
     * 请求方法
     */
    private Method method;

    private Integer requestBodyParameterIndex;

    /**
     * 请求方法的参数名
     */
    private String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    private Object[] parameterValues;

    /**
     * 请求开始时间
     */
    private long processedAt;

    private static final long serialVersionUID = 1L;

}