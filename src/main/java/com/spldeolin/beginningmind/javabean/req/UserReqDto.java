package com.spldeolin.beginningmind.javabean.req;

import org.hibernate.validator.constraints.Length;
import com.spldeolin.beginningmind.entity.UserEntity;
import lombok.Data;

/**
 * “用户”Input类
 *
 * @author Deolin 2018/8/4
 */
@Data
public class UserReqDto {

    /**
     * 通用字段 数据版本
     */
    private Integer version;

    /**
     * 用户编号
     */
    @Length(max = 1)
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

    public UserEntity toEntity(Long id) {
        UserEntity userEntity = toEntity();
        userEntity.setId(id);
        return userEntity;
    }

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setSerialNumber(this.serialNumber);
        userEntity.setName(this.name);
        userEntity.setMobile(this.mobile);
        userEntity.setEmail(this.email);
        return userEntity;
    }

}