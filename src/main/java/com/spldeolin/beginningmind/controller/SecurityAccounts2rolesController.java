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
import com.spldeolin.beginningmind.input.SecurityAccounts2rolesInput;
import com.spldeolin.beginningmind.service.SecurityAccounts2rolesService;

/**
 * “帐号与权限的关联”管理
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("security_accounts2roles")
@Validated
public class SecurityAccounts2rolesController {

    @Autowired
    private SecurityAccounts2rolesService securityAccounts2rolesService;

    /**
     * 创建一个“帐号与权限的关联”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid SecurityAccounts2rolesInput securityAccounts2rolesInput) {
        return RequestResult.success(securityAccounts2rolesService.createEX(securityAccounts2rolesInput.toModel()));
    }

    /**
     * 获取一个“帐号与权限的关联”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(
                securityAccounts2rolesService.get(id).orElseThrow(() -> new ServiceException("帐号与权限的关联不存在或是已被删除")));
    }

    /**
     * 更新一个“帐号与权限的关联”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id,
            @RequestBody @Valid SecurityAccounts2rolesInput securityAccounts2rolesInput) {
        securityAccounts2rolesService.updateEX(securityAccounts2rolesInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“帐号与权限的关联”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        securityAccounts2rolesService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“帐号与权限的关联”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(securityAccounts2rolesService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“帐号与权限的关联”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(securityAccounts2rolesService.deleteEX(ids));
    }

}