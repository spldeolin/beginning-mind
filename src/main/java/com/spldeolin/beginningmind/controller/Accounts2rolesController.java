package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.spldeolin.beginningmind.aspect.annotation.*;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.Accounts2rolesInput;
import com.spldeolin.beginningmind.service.Accounts2rolesService;
import com.spldeolin.beginningmind.valid.ValidableList;
import com.spldeolin.beginningmind.api.exception.ServiceException;

/**
 * “帐号与权限的关联”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("accounts2roles")
@Validated
public class Accounts2rolesController {

    @Autowired
    private Accounts2rolesService accounts2rolesService;

    /**
     * 创建一个“帐号与权限的关联”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid Accounts2rolesInput accounts2rolesInput) {
        return RequestResult.success(accounts2rolesService.createEX(accounts2rolesInput.toModel()));
    }

    /**
     * 获取一个“帐号与权限的关联”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(accounts2rolesService.get(id).orElseThrow(() -> new ServiceException("帐号与权限的关联不存在或是已被删除")));
    }

    /**
     * 更新一个“帐号与权限的关联”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid Accounts2rolesInput accounts2rolesInput) {
        accounts2rolesService.updateEX(accounts2rolesInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“帐号与权限的关联”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        accounts2rolesService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“帐号与权限的关联”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(accounts2rolesService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“帐号与权限的关联”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(accounts2rolesService.deleteEX(ids));
    }

}