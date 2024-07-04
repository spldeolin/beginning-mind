package com.spldeolin.beginningmind.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.spldeolin.beginningmind.app.entity.OrdersEntity;
import com.spldeolin.beginningmind.app.javabean.cond.QueryOrdersCond;
import com.spldeolin.beginningmind.app.javabean.record.QueryOrderdetailsRecord;
import com.spldeolin.beginningmind.app.javabean.req.ListOrdersReqDto;
import com.spldeolin.beginningmind.app.javabean.resp.ListOrdersRespDto;
import com.spldeolin.beginningmind.app.mapper.OrderdetailsMapper;
import com.spldeolin.beginningmind.app.mapper.OrdersMapper;
import com.spldeolin.beginningmind.app.service.ListOrdersService;
import com.spldeolin.satisficing.app.util.PageUtils;
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

    @Autowired
    private OrderdetailsMapper orderdetailsMapper;

    @Override
    public PageInfo<ListOrdersRespDto> listOrders(ListOrdersReqDto req) {
        log.info("dates={}", req.getOrderDateLefts());
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        final QueryOrdersCond queryOrdersCond = new QueryOrdersCond();
        queryOrdersCond.setStudentNumber(req.getStudentNumber());
        queryOrdersCond.setStudentNumberEx(req.getStudentNumbers());
        queryOrdersCond.setOrderDate(req.getOrderDateLeft());
        queryOrdersCond.setOrderDateEx(req.getOrderDateRight());
        List<OrdersEntity> orders = ordersMapper.queryOrders(queryOrdersCond);
        if (orders.size() == 0) {
            return new PageInfo<>();
        }

        List<Integer> orderNumbers = orders.stream().map(OrdersEntity::getOrderNumber).collect(Collectors.toList());
        Multimap<Integer/*orderNumber*/, String/*courseCodes*/> courseCodes = ArrayListMultimap.create();
        List<QueryOrderdetailsRecord> queryOrderdetails = orderdetailsMapper.queryOrderdetails(orderNumbers);
        queryOrderdetails.forEach(one -> courseCodes.put(one.getOrderNumber(), one.getCourseCode()));

        List<ListOrdersRespDto> dtos = Lists.newArrayList();
        for (OrdersEntity order : orders) {
            ListOrdersRespDto dto = new ListOrdersRespDto();
            dto.setOrderNumber(order.getOrderNumber());
            dto.setOrderDate(order.getOrderDate());
            dto.setRequiredDate(order.getRequiredDate());
            dto.setShippedDate(order.getShippedDate());
            dto.setStatus(order.getStatus());
            dto.setComments(order.getComments());
            dto.setCourseCodes(courseCodes.get(order.getOrderNumber()));
            dtos.add(dto);
        }

        return PageUtils.transferType(orders, dtos);
    }

}
