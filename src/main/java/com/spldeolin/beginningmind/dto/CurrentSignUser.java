package com.spldeolin.beginningmind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CurrentSignUser implements Serializable, AuthCachePrincipal {

    private static final long serialVersionUID = 1L;

    private String username;

    @Override
    public String getAuthCacheKey() {
        return username;
    }

}