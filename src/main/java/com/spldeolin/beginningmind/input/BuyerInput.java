/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */
package com.spldeolin.beginningmind.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import com.spldeolin.beginningmind.model.Buyer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * “买家”Input类
 *
 * @author Deolin 2018/5/26
 */
@Data
@Accessors(chain = true)
public class BuyerInput implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 审计字段 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 昵称
     */
    @Size(max = 255)
    private String nickname;

    /**
     * 钱包余额
     */
    @Digits(integer = 8, fraction = 2)
    private BigDecimal walletBalance;

    /**
     * VIP等级（最低0，代表非VIP）
     */
    private Integer vipLevel;

    private static final long serialVersionUID = 1L;

    public Buyer toModel() {
        return Buyer.builder().id(id).updatedAt(updatedAt).nickname(nickname).walletBalance(walletBalance).vipLevel(
                vipLevel).build();
    }

}