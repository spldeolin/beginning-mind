package com.spldeolin.beginningmind.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
@AllArgsConstructor
public class SignerProfileVO {

    private String userName;

    private List<Long> permissionIds;

}