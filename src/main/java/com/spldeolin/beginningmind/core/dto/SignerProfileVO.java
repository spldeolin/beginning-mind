package com.spldeolin.beginningmind.core.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
public class SignerProfileVO implements Serializable {

    private String userName;

    private List<Long> permissionIds;

    private static final long serialVersionUID = 1L;

    public SignerProfileVO(String userName, List<Long> permissionIds) {
        this.userName = userName;
        this.permissionIds = permissionIds;
    }
}