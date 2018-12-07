package com.spldeolin.beginningmind.core.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.spldeolin.beginningmind.core.model.Permission;
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

    /**
     * 被授予的权限
     */
    private List<Permission> permissions;

    private static final long serialVersionUID = 1L;

}