/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */
package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.input.SecurityRoleInput;
import com.spldeolin.beginningmind.service.SecurityRoleService;

/**
 * “角色”管理
 *
 * @author Deolin 2018/5/26
 */
@RestController
@RequestMapping("/securityRole")
@Validated
public class SecurityRoleController {

    @Autowired
    private SecurityRoleService securityRoleService;

    /**
     * 创建一个“角色”
     */
    @PostMapping("/create")
    Object create(@RequestBody @Valid SecurityRoleInput securityRoleInput) {
        return securityRoleService.createEX(securityRoleInput.toModel());
    }

    /**
     * 获取一个“角色”
     */
    @GetMapping("/get/{id}")
    Object get(@PathVariable Long id) {
        return securityRoleService.get(id).orElseThrow(() -> new ServiceException("角色不存在或是已被删除"));
    }

    /**
     * 更新一个“角色”
     */
    @PostMapping("/update/{id}")
    void update(@PathVariable Long id, @RequestBody @Valid SecurityRoleInput securityRoleInput) {
        securityRoleService.updateEX(securityRoleInput.toModel().setId(id));
    }

    /**
     * 删除一个“角色”
     */
    @PostMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        securityRoleService.deleteEX(id);
    }

    /**
     * 获取一批“角色”
     */
    @GetMapping("/search")
    Object page(@RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") @Max(1000) Integer pageSize) {
        return securityRoleService.page(pageNo, pageSize);
    }

    /**
     * 删除一批“角色”
     */
    @PostMapping("/batchDelete")
    Object delete(@RequestBody List<Long> ids) {
        return securityRoleService.deleteEX(ids);
    }

}