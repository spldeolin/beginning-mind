package com.spldeolin.beginningmind.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Deolin 2018/11/16
 */
public interface ImageService {

    String upload(MultipartFile multipartFile);

}
