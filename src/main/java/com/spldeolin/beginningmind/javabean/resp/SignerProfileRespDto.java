package com.spldeolin.beginningmind.javabean.resp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deolin 2018/05/28
 */
@Data
@AllArgsConstructor
public class SignerProfileRespDto {

    private String userName;

    private List<Long> permissionIds;

}