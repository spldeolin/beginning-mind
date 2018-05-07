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
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.SecurityRoleInput;
import com.spldeolin.beginningmind.service.SecurityRoleService;

/**
 * “角色”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("/security_roles")
@Validated
public class SecurityRoleController {

    @Autowired
    private SecurityRoleService securityRoleService;

    /**
     * 创建一个“角色”
     */
    @PostMapping("/")
    public RequestResult create(@RequestBody @Valid SecurityRoleInput securityRoleInput) {
        return RequestResult.success(securityRoleService.createEX(securityRoleInput.toModel()));
    }

    /**
     * 获取一个“角色”
     */
    @GetMapping("/{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(
                securityRoleService.get(id).orElseThrow(() -> new ServiceException("角色不存在或是已被删除")));
    }

    /**
     * 更新一个“角色”
     */
    @PutMapping("/{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid SecurityRoleInput securityRoleInput) {
        securityRoleService.updateEX(securityRoleInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“角色”
     */
    @DeleteMapping("/{id}")
    public RequestResult delete(@PathVariable Long id) {
        securityRoleService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“角色”
     */
    @GetMapping("/")
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(securityRoleService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“角色”
     */
    @PutMapping("/batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(securityRoleService.deleteEX(ids));
    }

}