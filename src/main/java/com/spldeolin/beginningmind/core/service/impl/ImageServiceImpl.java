package com.spldeolin.beginningmind.core.service.impl;

import java.io.File;
import java.io.InputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.api.exception.BizException;
import com.spldeolin.beginningmind.core.constant.DirectoryName;
import com.spldeolin.beginningmind.core.service.ImageService;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/16
 */
@Service
@Log4j2
public class ImageServiceImpl implements ImageService {


    @Autowired
    private CoreProperties coreProperties;

    @Override
    public String uploadToLocal(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BizException("文件不存在。");
        }

        String location = coreProperties.getFile().getLocation();
        String mapping = coreProperties.getFile().getMapping();
        try (InputStream in = multipartFile.getInputStream()) {
            // 文件名
            String fileMd5 = DigestUtils.md5Hex(in);
            String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String destFileName = fileMd5 + FilenameUtils.EXTENSION_SEPARATOR + fileExtension;
            // 生成文件
            File destFile = new File(location + DirectoryName.IMAGE_DIRECTORY + File.separator + destFileName);
            if (!destFile.exists()) {
                multipartFile.transferTo(destFile);
            }
            // 映射
            return coreProperties.getAddress() + mapping + DirectoryName.IMAGE_DIRECTORY + "/" + destFileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
