package com.spldeolin.beginningmind.core.input;

import java.io.Serializable;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * “用户”Input类
 *
 * @author Deolin 2018/8/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserInput implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 通用字段 数据版本
     */
    private Integer version;

    /**
     * 用户编号
     */
    private String serialNumber;

    /**
     * 名字
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * E-mail
     */
    private String email;

    private static final long serialVersionUID = 1L;

    public UserEntity toEntity() {
        return UserEntity.builder().id(id).version(version).serialNumber(serialNumber).name(name).mobile(mobile).email(email)
                .build();
    }

}