package com.spldeolin.beginningmind.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.crazycake.shiro.AuthCachePrincipal;
import com.spldeolin.beginningmind.model.SecurityAccount;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前登录者的信息
 */
@Data
@Builder
@Accessors(chain = true)
public class CurrentSigner implements Serializable, AuthCachePrincipal {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 帐号
     */
    private SecurityAccount securityAccount;

    /**
     * 登录时间
     */
    private LocalDateTime signedAt;

    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthCacheKey() {
        return securityAccount.getUsername();
    }

}