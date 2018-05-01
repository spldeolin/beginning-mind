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
import com.spldeolin.beginningmind.input.SecurityPermissionInput;
import com.spldeolin.beginningmind.service.SecurityPermissionService;

/**
 * “权限”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("security_permissions")
@Validated
public class SecurityPermissionController {

    @Autowired
    private SecurityPermissionService securityPermissionService;

    /**
     * 创建一个“权限”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid SecurityPermissionInput securityPermissionInput) {
        return RequestResult.success(securityPermissionService.createEX(securityPermissionInput.toModel()));
    }

    /**
     * 获取一个“权限”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(
                securityPermissionService.get(id).orElseThrow(() -> new ServiceException("权限不存在或是已被删除")));
    }

    /**
     * 更新一个“权限”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id,
            @RequestBody @Valid SecurityPermissionInput securityPermissionInput) {
        securityPermissionService.updateEX(securityPermissionInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“权限”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        securityPermissionService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“权限”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(securityPermissionService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“权限”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(securityPermissionService.deleteEX(ids));
    }

}