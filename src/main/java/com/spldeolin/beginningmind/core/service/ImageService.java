package com.spldeolin.beginningmind.core.service;

import static com.spldeolin.beginningmind.core.constant.Abbreviation.sep;

import java.io.File;
import java.io.InputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import lombok.SneakyThrows;

/**
 * 图片
 *
 * @author Deolin/05/28
 */
@Service
public class ImageService {

    public static final String IMAGE_DIRECTORY = "image";

    @Autowired
    private CoreProperties coreProperties;

    public String upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new ServiceException("文件不存在。");
        }
        return uploadToLocal(multipartFile);
    }

    @SneakyThrows
    private String uploadToLocal(MultipartFile multipartFile) {
        String location = coreProperties.getFile().getLocation();
        String mapping = coreProperties.getFile().getMapping();
        try (InputStream in = multipartFile.getInputStream()) {
            // 文件名
            String fileMd5 = DigestUtils.md5Hex(in);
            String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String destFileName = fileMd5 + FilenameUtils.EXTENSION_SEPARATOR + fileExtension;
            // 生成文件
            File destFile = new File(location + IMAGE_DIRECTORY + sep + destFileName);
            if (!destFile.exists()) {
                multipartFile.transferTo(destFile);
            }
            // 映射
            return coreProperties.getAddress() + mapping + IMAGE_DIRECTORY + "/" + destFileName;
        }
    }

}
