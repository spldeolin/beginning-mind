package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.spldeolin.beginningmind.aspect.annotation.*;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.PermissionInput;
import com.spldeolin.beginningmind.service.PermissionService;
import com.spldeolin.beginningmind.valid.ValidableList;
import com.spldeolin.beginningmind.api.exception.ServiceException;

/**
 * “权限”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("permissions")
@Validated
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 创建一个“权限”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid PermissionInput permissionInput) {
        return RequestResult.success(permissionService.createEX(permissionInput.toModel()));
    }

    /**
     * 获取一个“权限”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(permissionService.get(id).orElseThrow(() -> new ServiceException("权限不存在或是已被删除")));
    }

    /**
     * 更新一个“权限”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid PermissionInput permissionInput) {
        permissionService.updateEX(permissionInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“权限”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        permissionService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“权限”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(permissionService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“权限”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(permissionService.deleteEX(ids));
    }

}