/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.service;

import java.util.List;
import com.spldeolin.beginningmind.core.api.CommonService;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.dto.IdNameDTO;
import com.spldeolin.beginningmind.core.dto.OrganizationTreeDTO;
import com.spldeolin.beginningmind.core.model.Organization;

/**
 * “组织架构”业务
 *
 * @author Deolin 2018/8/28
 */
public interface OrganizationService extends CommonService<Organization> {

    Long createEX(Organization organization);

    Organization getEX(Long id);

    void updateEX(Organization organization);

    String deleteEX(List<Long> ids);

    Page<Organization> page(PageParam pageParam);

    OrganizationTreeDTO tree();

    List<IdNameDTO> tiny();

}