package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.spldeolin.beginningmind.aspect.annotation.*;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.RoleInput;
import com.spldeolin.beginningmind.service.RoleService;
import com.spldeolin.beginningmind.valid.ValidableList;
import com.spldeolin.beginningmind.api.exception.ServiceException;

/**
 * “角色”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("roles")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 创建一个“角色”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid RoleInput roleInput) {
        return RequestResult.success(roleService.createEX(roleInput.toModel()));
    }

    /**
     * 获取一个“角色”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(roleService.get(id).orElseThrow(() -> new ServiceException("角色不存在或是已被删除")));
    }

    /**
     * 更新一个“角色”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid RoleInput roleInput) {
        roleService.updateEX(roleInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“角色”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        roleService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“角色”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(roleService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“角色”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(roleService.deleteEX(ids));
    }

}