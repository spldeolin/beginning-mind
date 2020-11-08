package com.spldeolin.beginningmind.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.spldeolin.beginningmind.ancestor.EntityAncestor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 为Allison 1875准备的示例表
 * <p>allison1875_demo
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Allison1875DemoEntity extends EntityAncestor {

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    private Boolean deleteFlag;

    /**
     * 属性A
     * <p>varchar1
     * <p>长度：255
     */
    private String varchar1;

    /**
     * 属性B
     * <p>varchar2
     * <p>长度：2333
     */
    private String varchar2;

    /**
     * 属性C
     * <p>char1
     * <p>长度：9
     */
    private String char1;

    /**
     * 属性D
     * <p>char2
     * <p>长度：233
     */
    private String char2;

    /**
     * 属性E
     * <p>text1
     * <p>长度：65535
     */
    private String text1;

    /**
     * 属性F
     * <p>text2
     * <p>长度：65535
     */
    private String text2;

    /**
     * 属性G
     * <p>tinyint1
     */
    private Byte tinyint1;

    /**
     * 属性H
     * <p>longtext2
     * <p>长度：4294967295
     */
    private String longtext2;

    /**
     * 属性I
     * <p>longtext1
     * <p>长度：4294967295
     */
    private String longtext1;

    /**
     * 属性J
     * <p>tinyint2
     */
    private Byte tinyint2;

    /**
     * 属性K
     * <p>int1
     */
    private Integer int1;

    /**
     * 属性L
     * <p>int2
     */
    private Integer int2;

    /**
     * 属性M
     * <p>boolean1
     */
    private Boolean boolean1;

    /**
     * 属性N
     * <p>boolean2
     */
    private Boolean boolean2;

    /**
     * 属性O
     * <p>datetime1
     */
    private Date datetime1;

    /**
     * 属性P
     * <p>datetime2
     */
    private Date datetime2;

    /**
     * 属性Q
     * <p>decimal1
     */
    private BigDecimal decimal1;

    /**
     * 属性R
     * <p>decimal2
     */
    private BigDecimal decimal2;

}
