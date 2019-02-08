package com.spldeolin.beginningmind.core.controller;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.core.annotation.RestMapping;
import com.spldeolin.beginningmind.core.service.ImageService;

/**
 * 图片
 *
 * @author Deolin 2018/05/28
 */
@RestMapping("/image")
@Validated
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 普通上传
     */
    @PostMapping("/upload")
    String upload(@RequestParam("image") @NotNull MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }

}