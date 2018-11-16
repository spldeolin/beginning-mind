package com.spldeolin.beginningmind.core.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Deolin 2018/11/16
 */
public interface ImageService {

    String uploadToLocal(MultipartFile multipartFile);

}
