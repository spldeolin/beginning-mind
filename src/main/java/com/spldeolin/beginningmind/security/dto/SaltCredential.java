package com.spldeolin.beginningmind.security.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 当前登录的最终密码信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SaltCredential implements Serializable {

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
