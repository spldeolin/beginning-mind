package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.spldeolin.beginningmind.aspect.annotation.*;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.Roles2permissionsInput;
import com.spldeolin.beginningmind.service.Roles2permissionsService;
import com.spldeolin.beginningmind.valid.ValidableList;
import com.spldeolin.beginningmind.api.exception.ServiceException;

/**
 * “角色与权限的关联”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("roles2permissions")
@Validated
public class Roles2permissionsController {

    @Autowired
    private Roles2permissionsService roles2permissionsService;

    /**
     * 创建一个“角色与权限的关联”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid Roles2permissionsInput roles2permissionsInput) {
        return RequestResult.success(roles2permissionsService.createEX(roles2permissionsInput.toModel()));
    }

    /**
     * 获取一个“角色与权限的关联”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(roles2permissionsService.get(id).orElseThrow(() -> new ServiceException("角色与权限的关联不存在或是已被删除")));
    }

    /**
     * 更新一个“角色与权限的关联”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid Roles2permissionsInput roles2permissionsInput) {
        roles2permissionsService.updateEX(roles2permissionsInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“角色与权限的关联”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        roles2permissionsService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“角色与权限的关联”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(roles2permissionsService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“角色与权限的关联”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(roles2permissionsService.deleteEX(ids));
    }

}