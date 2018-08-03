package com.spldeolin.beginningmind.core.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.crazycake.shiro.AuthCachePrincipal;
import com.spldeolin.beginningmind.core.model.SecurityUser;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前登录者的信息
 */
@Data
@Builder
@Accessors(chain = true)
public class CurrentSignerDTO implements Serializable, AuthCachePrincipal {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户
     */
    private SecurityUser securityUser;

    /**
     * 登录时间
     */
    private LocalDateTime signedAt;

    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthCacheKey() {
        return securityUser.getUsername();
    }

}