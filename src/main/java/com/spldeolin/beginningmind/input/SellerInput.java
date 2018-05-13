package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.Seller;
import lombok.Data;

/**
 * “卖家”Input类
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Data
public class SellerInput implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 审计字段 更新时间
     */
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 审计字段 是否被删除
     */
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    /**
     * 昵称
     */
    @Size(max = 255)
    private String nickname;

    private static final long serialVersionUID = 1L;

    public Seller toModel() {
        Seller model = Seller.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}