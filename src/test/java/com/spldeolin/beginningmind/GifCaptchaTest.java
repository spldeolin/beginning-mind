package com.spldeolin.beginningmind;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import com.spldeolin.beginningmind.security.GifCaptcha;

public class GifCaptchaTest {

    @Test
    public void testGifCaptcha() {
        File file = new File("C:\\beginning-mind-static\\1.gif");
        try (OutputStream os = FileUtils.openOutputStream(file)) {
            GifCaptcha gifCaptcha = new GifCaptcha();
            gifCaptcha.out(os);
            System.out.println(gifCaptcha.getCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
