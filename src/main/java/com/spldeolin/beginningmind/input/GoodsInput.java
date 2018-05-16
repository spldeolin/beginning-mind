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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spldeolin.beginningmind.model.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * “商品”Input类
 *
 * @author Deolin 2018/5/16
 */
@Data
@Accessors(chain = true)
public class GoodsInput implements Serializable {

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
     * 商品名
     */
    @Size(max = 255)
    private String name;

    /**
     * 净重（单位g）
     */
    @JsonProperty("net_weight")
    private Integer netWeight;

    /**
     * 单价
     */
    @JsonProperty("unit_price")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal unitPrice;

    /**
     * 库存余量
     */
    @JsonProperty("stock_balance")
    private Integer stockBalance;

    private static final long serialVersionUID = 1L;

    public Goods toModel() {
        return Goods.builder().id(id).updatedAt(updatedAt).name(name).netWeight(netWeight).unitPrice(
                unitPrice).stockBalance(stockBalance).build();
    }

}