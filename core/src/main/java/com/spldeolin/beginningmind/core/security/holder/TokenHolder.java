package com.spldeolin.beginningmind.core.security.holder;

import java.util.List;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.spldeolin.beginningmind.core.entity.SecurityAccessTokenEntity;
import lombok.extern.log4j.Log4j;

/**
 * @author Deolin 2019-02-23
 */
@Log4j
public class TokenHolder {

    /**
     * mappingMethod, mappingPath, token
     */
    private Table<String, String, String> tokenTable = HashBasedTable.create();

    public void holdTokens(List<SecurityAccessTokenEntity> securityAccessTokens) {
        securityAccessTokens
                .forEach(one -> tokenTable.put(one.getMappingMethod(), one.getMappingPath(), one.getToken()));
        log.info("Token Holder initialized.");
    }

    public String getToken(String mappingMethod, String mappingPath) {
        return tokenTable.get(mappingMethod, mappingPath);
    }

}
