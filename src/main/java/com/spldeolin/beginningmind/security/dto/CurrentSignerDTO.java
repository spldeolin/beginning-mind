package com.spldeolin.beginningmind.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.spldeolin.beginningmind.entity.PermissionEntity;
import com.spldeolin.beginningmind.entity.UserEntity;
import lombok.Data;

/**
 * 当前登录者的信息
 *
 * @author Deolin
 */
@Data
public class CurrentSignerDTO implements Serializable {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户
     */
    private UserEntity user;

    /**
     * 登录时间
     */
    private LocalDateTime signedAt;

    /**
     * 被授予的权限
     */
    private List<PermissionEntity> permissions;

    private static final long serialVersionUID = 1L;

}