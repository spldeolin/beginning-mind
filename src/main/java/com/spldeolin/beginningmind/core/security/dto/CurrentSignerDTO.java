package com.spldeolin.beginningmind.core.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.spldeolin.beginningmind.core.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前登录者的信息
 *
 * @author Deolin
 */
@Data
@Builder
@Accessors(chain = true)
public class CurrentSignerDTO implements Serializable {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户
     */
    private User user;

    /**
     * 登录时间
     */
    private LocalDateTime signedAt;

    // TODO private 拥有的权限

    // TODO private 拥有的菜单

    private static final long serialVersionUID = 1L;

}