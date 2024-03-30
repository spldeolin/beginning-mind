package com.spldeolin.beginningmind.app.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.app.entity.OrdersEntity;
import com.spldeolin.beginningmind.app.javabean.req.ListOrdersReqDto;
import com.spldeolin.beginningmind.app.javabean.resp.ListOrdersRespDto;
import com.spldeolin.beginningmind.app.mapper.OrdersMapper;
import com.spldeolin.beginningmind.app.service.ListOrdersService;
import com.spldeolin.satisficing.framework.app.util.PageUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Allison 1875 Lot No: HT1001S-BAE3BC9B
 *
 * @author Deolin
 */
@Slf4j
@Service
public class ListOrdersServiceImpl implements ListOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public PageInfo<ListOrdersRespDto> listOrders(ListOrdersReqDto req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<OrdersEntity> ordersList = ordersMapper.queryOrders(req.getCustomerNumber(), req.getOrderDateLeft(),
                req.getOrderDateRight());

        List<ListOrdersRespDto> dtos = Lists.newArrayList();
        for (OrdersEntity orders : ordersList) {
            ListOrdersRespDto dto = new ListOrdersRespDto();
            dto.setOrderNumber(orders.getOrderNumber());
            dto.setOrderDate(orders.getOrderDate());
            dto.setRequiredDate(orders.getRequiredDate());
            dto.setShippedDate(orders.getShippedDate());
            dto.setStatus(orders.getStatus());
            dto.setComments(orders.getComments());
            dtos.add(dto);
        }

        return PageUtils.transferType(ordersList, dtos);
    }

}
