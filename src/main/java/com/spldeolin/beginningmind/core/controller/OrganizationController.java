/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.dto.IdNameDTO;
import com.spldeolin.beginningmind.core.dto.OrganizationTreeDTO;
import com.spldeolin.beginningmind.core.input.OrganizationInput;
import com.spldeolin.beginningmind.core.model.Organization;
import com.spldeolin.beginningmind.core.service.OrganizationService;

/**
 * “组织架构”管理
 *
 * @author Deolin 2018/8/28
 */
@RestController
@RequestMapping("/organization")
@Validated
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 创建一个“组织架构”
     *
     * @param organizationInput 待创建的“组织架构”
     * @return 创建成功后生成的ID
     */
    @PostMapping("/create")
    Long create(@RequestBody @Valid OrganizationInput organizationInput) {
        return organizationService.createEX(organizationInput.toModel());
    }

    /**
     * 获取一个“组织架构”
     *
     * @param id 待获取“组织架构”的ID
     * @return 组织架构
     */
    @GetMapping("/get")
    Organization get(@RequestParam Long id) {
        return organizationService.getEX(id);
    }

    /**
     * 更新一个“组织架构”
     *
     * @param id 待更新“组织架构”的ID
     * @param organizationInput 待更新的“组织架构”
     */
    @PostMapping("/update")
    void update(@RequestParam Long id, @RequestBody @Valid OrganizationInput organizationInput) {
        organizationService.updateEX(organizationInput.toModel().setId(id));
    }

    /**
     * 获取一批“组织架构”
     *
     * @param pageParam 页码和每页条目数
     * @return “组织架构”分页
     */
    @GetMapping("/search")
    Page<Organization> search(@RequestParam(defaultValue = CoupledConstant.ROOT_ORGANIZATION_ID_S) Long parentId,
            PageParam pageParam) {
        return organizationService.page(pageParam);
    }

    /**
     * 删除一批“组织架构”
     *
     * @param ids 待删除“组织架构”的ID列表
     * @return 删除情况
     */
    @PostMapping("/batchDelete")
    String delete(@RequestBody @Size(min = 1) List<Long> ids) {
        return organizationService.deleteEX(ids);
    }

    /**
     * 组织架构树
     */
    @GetMapping("/tree")
    OrganizationTreeDTO tree() {
        return organizationService.tree();
    }

    /**
     * 组织架构一览
     */
    @GetMapping("/tiny")
    List<IdNameDTO> tiny() {
        return organizationService.tiny();
    }

}