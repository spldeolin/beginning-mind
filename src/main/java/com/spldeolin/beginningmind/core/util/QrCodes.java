package com.spldeolin.beginningmind.core.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * 工具类：二维码
 *
 * @author Deolin
 */
@UtilityClass
@Log4j2
public class QrCodes {

    /**
     * 生成二维码图片
     *
     * @param filePath 文件路径，以png为结尾
     * @param content 二维码信息
     */
    @SneakyThrows
    public static void generate(String filePath, String content) {
        QrCodes.encode(content, null, filePath, true);
    }

    /**
     * 生成二维码图片
     *
     * @param filePath 文件路径，以png为结尾
     * @param data 二维码信息，这个对象将会转化为JSON
     */
    @SneakyThrows
    public static void generate(String filePath, Object data) {
        QrCodes.encode(Jsons.toJson(data), null, filePath, true);
    }

    private static final String FORMAT = "PNG";

    private static final int QRCODE_SIZE = 300;

    private static final int LOGO_WIDTH = 140;

    private static final int LOGO_HEIGHT = 140;

    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        QrCodes.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source 二维码图片
     * @param logoPath LOGO图片地址
     * @param needCompress 是否压缩
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            throw new Exception("logo file not found.");
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO) 二维码文件名随机，文件名可能会有重复
     *
     * @param content 内容
     * @param logoPath LOGO地址
     * @param destPath 存放目录
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String logoPath, String destPath,
            boolean needCompress) throws Exception {
        BufferedImage image = QrCodes.createImage(content, logoPath, needCompress);
        File destFile = new File(destPath);
        ImageIO.write(image, FORMAT, destFile);
        return destFile.getName();
    }

    /**
     * 生成二维码(内嵌LOGO) 调用者指定二维码文件名
     *
     * @param content 内容
     * @param logoPath LOGO地址
     * @param destPath 存放目录
     * @param fileName 二维码文件名
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String logoPath, String destPath, String fileName,
            boolean needCompress) throws Exception {
        BufferedImage image = QrCodes.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length())
                + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir． (mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    private static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("创建失败");
            }
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content 内容
     * @param logoPath LOGO地址
     * @param destPath 存储地址
     */
    public static String encode(String content, String logoPath, String destPath) throws Exception {
        return QrCodes.encode(content, logoPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param destPath 存储地址
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String destPath, boolean needCompress) throws Exception {
        return QrCodes.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param destPath 存储地址
     */
    public static String encode(String content, String destPath) throws Exception {
        return QrCodes.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content 内容
     * @param logoPath LOGO地址
     * @param output 输出流
     * @param needCompress 是否压缩LOGO
     */
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QrCodes.createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output 输出流
     */
    public static void encode(String content, OutputStream output) throws Exception {
        QrCodes.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file 二维码图片
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     */
    public static String decode(String path) throws Exception {
        return QrCodes.decode(new File(path));
    }

}
