package com.spldeolin.beginningmind.app.service;

import com.github.pagehelper.PageInfo;
import com.spldeolin.beginningmind.app.javabean.req.ListOrdersReqDto;
import com.spldeolin.beginningmind.app.javabean.resp.ListOrdersRespDto;

/**
 * <p>Allison 1875 Lot No: HT1001S-BAE3BC9B
 *
 * @author Deolin
 */
public interface ListOrdersService {

    PageInfo<ListOrdersRespDto> listOrders(ListOrdersReqDto req);

}
