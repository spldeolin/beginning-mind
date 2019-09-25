package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.input.UserInput;
import com.spldeolin.beginningmind.service.SignService;
import com.spldeolin.beginningmind.service.UserService;

/**
 * “用户”管理
 *
 * @author Deolin 2018/8/4
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SignService signService;

    /**
     * 创建一个“用户”
     *
     * @param userInput 待创建的“用户”
     * @return 创建成功后生成的ID
     */
    @PostMapping("/create")
    Long create(@RequestBody @Valid UserInput userInput) {
        return userService.createUser(userInput.toEntity());
    }

    /**
     * 获取一个“用户”
     *
     * @param id 待获取“用户”的ID
     * @return 用户
     */
    @GetMapping("/get")
    UserEntity get(@RequestParam Long id) {
        return userService.getUser(id);
    }

    /**
     * 更新一个“用户”
     *
     * @param id 待更新“用户”的ID
     * @param userInput 待更新的“用户”
     */
    @PostMapping("/update")
    void update(@RequestParam Long id, @RequestBody @Valid UserInput userInput) {
        userService.updateUser(userInput.toEntity(id));
    }

    /**
     * 删除一个“用户”
     *
     * @param id 待删除“用户”的ID
     */
    @PostMapping("/delete")
    void delete(@RequestParam Long id) {
        userService.deleteUser(id);
    }

    /**
     * 删除一批“用户”
     *
     * @param ids 待删除“用户”的ID列表
     * @return 删除情况
     */
    @PostMapping("/batchDelete")
    String delete(@RequestBody List<Long> ids) {
        return userService.deleteUsers(ids);
    }

}