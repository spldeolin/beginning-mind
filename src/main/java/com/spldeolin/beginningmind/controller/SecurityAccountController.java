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
import com.spldeolin.beginningmind.controller.annotation.PermissionDisplayName;
import com.spldeolin.beginningmind.input.SecurityAccountInput;
import com.spldeolin.beginningmind.service.SecurityAccountService;

/**
 * “帐号”管理
 *
 * @author Deolin 2018/5/26
 */
@RestController
@RequestMapping("/securityAccount")
@Validated
public class SecurityAccountController {

    @Autowired
    private SecurityAccountService securityAccountService;

    /**
     * 创建一个“帐号”
     */
    @PermissionDisplayName("创建帐号")
    @PostMapping("/create")
    Object create(@RequestBody @Valid SecurityAccountInput securityAccountInput) {
        return securityAccountService.createEX(securityAccountInput.toModel());
    }

    /**
     * 获取一个“帐号”
     */
    @PermissionDisplayName("帐号详情")
    @GetMapping("/get/{id}")
    Object get(@PathVariable Long id) {
        return securityAccountService.get(id).orElseThrow(() -> new ServiceException("帐号不存在或是已被删除"));
    }

    /**
     * 更新一个“帐号”
     */
    @PermissionDisplayName("更新帐号")
    @PostMapping("/update/{id}")
    void update(@PathVariable Long id, @RequestBody @Valid SecurityAccountInput securityAccountInput) {
        securityAccountService.updateEX(securityAccountInput.toModel().setId(id));
    }

    /**
     * 删除一个“帐号”
     */
    @PermissionDisplayName("删除帐号")
    @PostMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        securityAccountService.deleteEX(id);
    }

    /**
     * 获取一批“帐号”
     */
    @PermissionDisplayName("帐号列表")
    @GetMapping("/search")
    Object page(@RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") @Max(1000) Integer pageSize) {
        return securityAccountService.page(pageNo, pageSize);
    }

    /**
     * 删除一批“帐号”
     */
    @PermissionDisplayName("批量删除帐号")
    @PostMapping("/batchDelete")
    Object delete(@RequestBody List<Long> ids) {
        return securityAccountService.deleteEX(ids);
    }

}