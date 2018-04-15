package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.spldeolin.cadeau.library.annotation.*;
import com.spldeolin.cadeau.library.dto.RequestResult;
import com.spldeolin.beginningmind.input.UserInput;
import com.spldeolin.beginningmind.service.UserService;
import com.spldeolin.cadeau.library.valid.ValidableList;
import com.spldeolin.cadeau.library.exception.ServiceException;

/**
 * “用户”管理
 *
 * @author Deolin 2018/4/15
 * @generator Cadeau Support
 */
@RestController
@RequestMapping("users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建一个“用户”
     */
    @PostMapping
    public RequestResult create(@RequestBody @Valid UserInput userInput) {
        return RequestResult.success(userService.createEX(userInput.toModel()));
    }

    /**
     * 获取一个“用户”
     */
    @GetMapping("{id}")
    public RequestResult get(@PathVariable Long id) {
        return RequestResult.success(userService.get(id).orElseThrow(() -> new ServiceException("用户不存在或是已被删除")));
    }

    /**
     * 更新一个“用户”
     */
    @PutMapping("{id}")
    public RequestResult update(@PathVariable Long id, @RequestBody @Valid UserInput userInput) {
        userService.updateEX(userInput.toModel().setId(id));
        return RequestResult.success();
    }

    /**
     * 删除一个“用户”
     */
    @DeleteMapping("{id}")
    public RequestResult delete(@PathVariable Long id) {
        userService.deleteEX(id);
        return RequestResult.success();
    }

    /**
     * 获取一批“用户”
     */
    @GetMapping
    public RequestResult page(@PageNo Integer pageNo, @PageSize Integer pageSize) {
        return RequestResult.success(userService.page(pageNo, pageSize));
    }

    /**
     * 删除一批“用户”
     */
    @PutMapping("batch_delete")
    public RequestResult delete(@RequestBody List<Long> ids) {
        return RequestResult.success(userService.deleteEX(ids));
    }

}