package com.spldeolin.beginningmind.core.vo;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
@AllArgsConstructor
public class SignerProfileVO implements Serializable {

    private String userName;

    private List<Long> permissionIds;

    private static final long serialVersionUID = 1L;

}