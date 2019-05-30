package com.spldeolin.beginningmind.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spldeolin.beginningmind.core.common.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 *
 * @author Deolin 2019-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_access_token")
public class SecurityAccessTokenEntity extends CommonEntity {

    /**
     * 映射方法（HTTP方法，e.g.: POST）
     */
    @TableField("mapping_method")
    private String mappingMethod;

    /**
     * 映射路由（控制器路由+方法路由，e.g.: /user/create）
     */
    @TableField("mapping_path")
    private String mappingPath;

    /**
     * TOKEN（30位随机大小写字母+数字）
     */
    @TableField("token")
    private String token;

    private static final long serialVersionUID = 1L;

}