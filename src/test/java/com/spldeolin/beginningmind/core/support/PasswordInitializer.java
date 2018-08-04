package com.spldeolin.beginningmind.core.support;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 密码初始器，将用户密码初始为一位0
 *
 * @author Deolin 2018/08/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class PasswordInitializer {

    @Autowired
    private UserService userService;

    @Test
    @SneakyThrows
    public void generate() {
        String salt = StringRandomUtils.generateVisibleAscii(32);
        String password = DigestUtils.sha512Hex(CoupledConstant.DEFAULT_PASSWORD_EX + salt);

        userService.updateEX(userService.getEX(1L).setSalt(salt).setPassword(password));

        log.info("结束");
    }

}