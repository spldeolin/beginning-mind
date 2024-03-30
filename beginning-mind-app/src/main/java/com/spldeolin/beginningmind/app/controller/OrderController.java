package com.spldeolin.beginningmind.app.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.spldeolin.beginningmind.app.javabean.req.ListOrdersReqDto;
import com.spldeolin.beginningmind.app.javabean.resp.ListOrdersRespDto;
import com.spldeolin.beginningmind.app.service.ListOrdersService;
import com.spldeolin.satisficing.framework.api.javabean.RequestResult;

/**
 * @author Deolin 2024-02-20
 */
@RestController
public class OrderController {

    @Autowired
    private ListOrdersService listOrdersService;

    /**
     * 订单列表
     * <p>Allison 1875 Lot No: HT1001S-BAE3BC9B
     */
    @PostMapping("listOrders")
    public RequestResult<PageInfo<ListOrdersRespDto>> listOrders(@RequestBody @Valid ListOrdersReqDto req) {
        return RequestResult.success(listOrdersService.listOrders(req));
    }

}
