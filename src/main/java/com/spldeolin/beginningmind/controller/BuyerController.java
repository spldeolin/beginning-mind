package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.aspect.annotation.PageNo;
import com.spldeolin.beginningmind.aspect.annotation.PageSize;
import com.spldeolin.beginningmind.dto.RequestResult;
import com.spldeolin.beginningmind.input.BuyerInput;
import com.spldeolin.beginningmind.service.BuyerService;

/**
 * “买家”管理
 *
 * @author Deolin 2018/4/30
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("buyers")
@Validated
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建一个“买家”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid BuyerInput buyerInput) {
        return RequestResult.success(buyerService.createEX(buyerInput.toModel()));
    }

    /**
     * 获取一个“买家”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(buyerService.get(id).orElseThrow(() -> new ServiceException("买家不存在或是已被删除")));
    }

    /**
     * 更新一个“买家”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid BuyerInput buyerInput) {
        buyerService.updateEX(buyerInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“买家”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        buyerService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“买家”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(buyerService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“买家”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(buyerService.deleteEX(ids));
    }

}