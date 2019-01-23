package com.spldeolin.beginningmind.launch.test.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 密码初始化
 *
 * @author Deolin 2019/01/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class PasswordInit {

    @Autowired
    private UserService userService;

    @Test
    public void initOne() {
        UserEntity user = userService.get(1L).orElseThrow(() -> new RuntimeException("用户不存在"));

        String salt = StringRandomUtils.generateVisibleAscii(32);
        String password = DigestUtils.sha512Hex(CoupledConstant.DEFAULT_PASSWORD_EX + salt);
        user.setSalt(salt);
        user.setPassword(password);

        log.info(userService.update(user));
    }

}