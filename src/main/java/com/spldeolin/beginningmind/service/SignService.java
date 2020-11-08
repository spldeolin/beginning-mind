package com.spldeolin.beginningmind.service;

import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.javabean.req.SignReqDto;
import com.spldeolin.beginningmind.javabean.resp.SignerProfileRespDto;

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
    SignerProfileRespDto signIn(SignReqDto input);

    /**
     * 登出
     */
    void signOut();

}