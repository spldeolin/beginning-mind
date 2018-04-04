package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.spldeolin.cadeau.library.valid.annotation.TextOption;
import com.spldeolin.beginningmind.model.User;

/**
 * “用户”Input类
 *
 * @author Deolin 2018/4/4
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
public class UserInput implements Serializable {

    /**
     * 通用字段 ID
     */
    private Long id;

    /**
     * 金额
     */
    @Digits(integer = 8, fraction = 2)
    private BigDecimal money;

    /**
     * 编号
     */
    @JsonProperty("serial_number")
    private Long serialNumber;

    /**
     * 百分率
     */
    @Digits(integer = 3, fraction = 2)
    private Double percent;

    /**
     * 富文本
     */
    @JsonProperty("rich_text")
    @Size(max = 65535)
    private String richText;

    /**
     * 通用字段 更新时间
     */
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /**
     * 名称
     */
    @Size(max = 64)
    private String name;

    /**
     * 随机盐
     */
    @Size(max = 9)
    private String salt;

    /**
     * 性别（m男 f女 n中 a无）
     */
    @Size(max = 2)
    @TextOption({"m" ,"f" ,"n" ,"a"})
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * “是否”
     */
    private Boolean flag;

    /**
     * 年月日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ymd;

    /**
     * 时分秒
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date hms;

    /**
     * 年月日时分秒
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ymdhms;

	private static final long serialVersionUID = 1L;

    public User toModel() {
        User model = User.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}