package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.spldeolin.beginningmind.valid.annotation.TextOption;
import com.spldeolin.beginningmind.model.Buyer;

/**
 * “买家”Input类
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Data
@NoArgsConstructor
public class BuyerInput implements Serializable {

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

    /**
     * 钱包余额
     */
    @JsonProperty("wallet_balance")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal walletBalance;

    /**
     * VIP等级（最低0，代表非VIP）
     */
    @JsonProperty("vip_level")
    private Integer vipLevel;

	private static final long serialVersionUID = 1L;

    public Buyer toModel() {
        Buyer model = Buyer.builder().build();
        BeanUtils.copyProperties(this, model);
        return model;
    }

}