package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.spldeolin.beginningmind.aspect.annotation.*;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.GoodsInput;
import com.spldeolin.beginningmind.service.GoodsService;
import com.spldeolin.beginningmind.valid.ValidableList;
import com.spldeolin.beginningmind.api.exception.ServiceException;

/**
 * “商品”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("goods")
@Validated
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 创建一个“商品”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid GoodsInput goodsInput) {
        return RequestResult.success(goodsService.createEX(goodsInput.toModel()));
    }

    /**
     * 获取一个“商品”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(goodsService.get(id).orElseThrow(() -> new ServiceException("商品不存在或是已被删除")));
    }

    /**
     * 更新一个“商品”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid GoodsInput goodsInput) {
        goodsService.updateEX(goodsInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“商品”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        goodsService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“商品”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(goodsService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“商品”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(goodsService.deleteEX(ids));
    }

}