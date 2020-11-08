package com.spldeolin.beginningmind.entity;

import com.spldeolin.beginningmind.ancestor.EntityAncestor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 * <p>security_access_token
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2019-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityAccessTokenEntity extends EntityAncestor {

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    private Boolean deleteFlag;

    /**
     * 映射方法（HTTP方法，e.g.: POST）
     * <p>mapping_method
     * <p>长度：255
     */
    private String mappingMethod;

    /**
     * 映射路由（控制器路由+方法路由，e.g.: /user/create）
     * <p>mapping_path
     * <p>长度：255
     */
    private String mappingPath;

    /**
     * TOKEN（30位随机大小写字母+数字）
     * <p>token
     * <p>长度：30
     */
    private String token;
}
