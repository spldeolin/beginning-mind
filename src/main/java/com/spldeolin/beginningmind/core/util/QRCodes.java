package com.spldeolin.beginningmind.core.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.spldeolin.beginningmind.core.api.exception.BizException;
import lombok.extern.log4j.Log4j2;
import sun.misc.BASE64Encoder;

/**
 * @author Deolin 2018/12/29
 */
@Log4j2
public class QRCodes {

    /**
     * 二维码的默认宽度
     */
    private static final int QRCODE_WIDTH = 300;

    /**
     * 二维码的默认高度
     */
    private static final int QRCODE_HEIGHT = 300;

    /**
     * 二维码LOGO的默认宽度
     */
    private static final int LOGO_WIDTH = 140;

    /**
     * 二维码LOGO的默认高度
     */
    private static final int LOGO_HEIGHT = 140;

    /**
     * 二维码文件格式、拓展名
     */
    private static final String FORMAT = "png";

    /**
     * 其他参数
     */
    private static final Map<EncodeHintType, Object> HINTS = new HashMap<>();

    static {
        HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 容错等级（High）
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码与图片边距
        HINTS.put(EncodeHintType.MARGIN, 2);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @return 二维码的BufferedImage对象
     */
    public static BufferedImage generateImage(String content) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH,
                    QRCODE_HEIGHT, HINTS);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            log.error("二维码生成失败", e);
            throw new BizException("二维码生成失败");
        }
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param stream 二维码的输出流对象
     */
    public static void generateOutput(String content, OutputStream stream) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH,
                    QRCODE_HEIGHT, HINTS);
            MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, stream);
        } catch (WriterException | IOException e) {
            log.error("二维码生成失败", e);
            throw new BizException("二维码生成失败");
        }
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @return 二维码的base64字符串
     */
    public static String generateBase64(String content) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        generateOutput(content, output);
        BASE64Encoder encoder = new BASE64Encoder();
        return "data:img/" + FORMAT + ";base64," + encoder.encode(output.toByteArray());
    }

    /**
     * 生成带LOGO的二维码
     *
     * @param content 内容
     * @return 二维码的base64字符串
     */
    public static String generateBase64(String content, String logoUrl) {
        BufferedImage qrCode = generateImage(content);
        insertLogo(qrCode, Https.getAsImage(logoUrl));

        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(qrCode, FORMAT, output);
            BASE64Encoder encoder = new BASE64Encoder();
            return "data:img/" + FORMAT + ";base64," + encoder.encode(output.toByteArray());
        } catch (IOException e) {
            log.error("二维码生成失败", e);
            throw new BizException("二维码生成失败");
        }
    }

    private static void insertLogo(BufferedImage qrCode, Image logo) {
        int width = logo.getWidth(null);
        int height = logo.getHeight(null);
        // 压缩LOGO
        if (width > LOGO_WIDTH) {
            width = LOGO_WIDTH;
        }
        if (height > LOGO_HEIGHT) {
            height = LOGO_HEIGHT;
        }
        Image image = logo.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        logo = image;
        // 插入LOGO
        Graphics2D graph = qrCode.createGraphics();
        int x = (QRCODE_WIDTH - width) / 2;
        int y = (QRCODE_HEIGHT - height) / 2;
        graph.drawImage(logo, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

}
