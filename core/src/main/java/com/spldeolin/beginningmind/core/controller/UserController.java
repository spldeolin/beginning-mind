package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.spldeolin.beginningmind.core.annotation.RestMapping;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.input.UserInput;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.service.UserService;

/**
 * “用户”管理
 *
 * @author Deolin 2018/8/4
 */
@RestMapping("/user")
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
        return userService.createEX(userInput.toEntity());
    }

    /**
     * 获取一个“用户”
     *
     * @param id 待获取“用户”的ID
     * @return 用户
     */
    @GetMapping("/get")
    UserEntity get(@RequestParam Long id) {
        return userService.getEX(id);
    }

    /**
     * 更新一个“用户”
     *
     * @param id 待更新“用户”的ID
     * @param userInput 待更新的“用户”
     */
    @PostMapping("/update")
    void update(@RequestParam Long id, @RequestBody @Valid UserInput userInput) {
        userService.updateEX(userInput.toEntity(id));
    }

    /**
     * 删除一个“用户”
     *
     * @param id 待删除“用户”的ID
     */
    @PostMapping("/delete")
    void delete(@RequestParam Long id) {
        userService.deleteEX(id);
    }

    /**
     * 删除一批“用户”
     *
     * @param ids 待删除“用户”的ID列表
     * @return 删除情况
     */
    @PostMapping("/batchDelete")
    String delete(@RequestBody List<Long> ids) {
        return userService.deleteEX(ids);
    }

}