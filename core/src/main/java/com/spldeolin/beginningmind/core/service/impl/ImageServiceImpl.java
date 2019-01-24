package com.spldeolin.beginningmind.core.service.impl;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.CoreProperties.OssProp;
import com.spldeolin.beginningmind.core.common.BizException;
import com.spldeolin.beginningmind.core.service.ImageService;
import com.spldeolin.beginningmind.core.util.WebContext;
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
    public String upload(MultipartFile multipartFile) {
        // 确保文件有内容，确保文件是图片
        ensureExist(multipartFile);
        ensureImage(WebContext.getRequest(), multipartFile);

        OssProp prop = coreProperties.getOss();
        String buckeName = prop.getBuckeName();
        String endPoint = prop.getEndPoint();
        OSS ossClient = new OSSClientBuilder().build(endPoint, prop.getAccessKeyId(), prop.getAccessKeySecret());

        try (InputStream input = multipartFile.getInputStream()) {
            String fileKey = prop.getFileHost() + "/" + DigestUtils.md5Hex(multipartFile.getBytes())
                    + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

            ossClient.putObject(buckeName, fileKey, input);

            return "https://" + buckeName + "." + endPoint + "/" + fileKey;
        } catch (IOException e) {
            log.error("图片上传失败", e);
            throw new BizException("图片上传失败");
        } finally {
            ossClient.shutdown();
        }
    }

    private void ensureExist(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BizException("文件不存在");
        }
    }

    private void ensureImage(HttpServletRequest request, MultipartFile multipartFile) {
        String mimeType = request.getServletContext().getMimeType(multipartFile.getOriginalFilename());
        if (!mimeType.startsWith("image/")) {
            throw new BizException("上传的文件不是图片，上传失败");
        }
    }

}
