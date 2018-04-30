package com.spldeolin.beginningmind.security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.crazycake.shiro.AuthCachePrincipal;
import com.spldeolin.beginningmind.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 当前登录者的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CurrentSigner implements Serializable, AuthCachePrincipal {

    /**
     * 帐号
     */
    private Account account;

    /**
     * 登录时间
     */
    private LocalDateTime signedAt;

    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthCacheKey() {
        return account.getUsername();
    }

}