package com.spldeolin.beginningmind.entity;

import com.spldeolin.beginningmind.ancestor.EntityAncestor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 建表语句
 * <p>__template
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateEntity extends EntityAncestor {

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    private Boolean deleteFlag;

}
