package com.spldeolin.beginningmind.core.security.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前登录的最终密码信息
 */
@Data
@Builder
@Accessors(chain = true)
public class SaltCredentialDTO implements Serializable {

    /**
     * 数据库内保存的“密码”字段
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    private static final long serialVersionUID = 1L;

}
