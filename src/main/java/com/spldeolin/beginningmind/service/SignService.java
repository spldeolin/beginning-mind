package com.spldeolin.beginningmind.service;

import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.vo.SignerProfileVO;

/**
 * 登录
 *
 * @author Deolin
 */
@Service
public interface SignService {

    /**
     * 登录
     */
    SignerProfileVO signIn(SignInput input);

    /**
     * 登出
     */
    void signOut();

}