package com.spldeolin.beginningmind.core.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.spldeolin.beginningmind.core.api.valid.annotation.Mobile;
import com.spldeolin.beginningmind.core.model.User;
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
     * 审计字段 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 名字
     */
    @NotBlank
    @Size(max = 255)
    private String name;

    /**
     * 手机号
     */
    @NotBlank
    @Size(max = 20)
    @Mobile
    private String mobile;

    /**
     * E-mail
     */
    @Size(max = 255)
    @Email
    private String email;

    private static final long serialVersionUID = 1L;

    public User toModel() {
        return User.builder().id(id).updatedAt(updatedAt).name(name).mobile(mobile).email(email).build();
    }

}
// id, updatedAt, name, mobile, email