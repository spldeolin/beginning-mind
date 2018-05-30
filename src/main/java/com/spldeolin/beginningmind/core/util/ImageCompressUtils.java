package com.spldeolin.beginningmind.core.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * 图片缩放工具类
 * <pre>
 * 支持按比例缩放、按指定宽高缩放
 * </pre>
 *
 * @author Deolin
 */
@UtilityClass
public class ImageCompressUtils {

    /***
     * 按指定的比例缩放图片
     *
     * @param sourceImagePath 源地址
     * @param destinationPath 改变大小后图片的地址
     * @param scale 缩放比例，如1.2
     */
    @SneakyThrows
    public static void scaleImage(String sourceImagePath, String destinationPath, double scale, String format) {
        File file = new File(sourceImagePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        width = parseDoubleToInt(width * scale);
        height = parseDoubleToInt(height * scale);

        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = outputImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        ImageIO.write(outputImage, format, new File(destinationPath));
    }

    /***
     * 将图片缩放到指定的高度或者宽度
     *
     * @param sourceImagePath 图片源地址
     * @param destinationPath 压缩完图片的地址
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param auto 是否自动保持图片的原高宽比例
     * @param format 图图片格式 例如 jpg
     */
    @SneakyThrows
    public static void scaleImageWithParams(String sourceImagePath, String destinationPath, int width, int height,
            boolean auto, String format) {
        File file = new File(sourceImagePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        if (auto) {
            ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage, width, height);
            width = paramsArrayList.get(0);
            height = paramsArrayList.get(1);
            System.out.println("自动调整比例，width=" + width + " height=" + height);
        }

        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = outputImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        ImageIO.write(outputImage, format, new File(destinationPath));
    }

    /**
     * 将double类型的数据转换为int，四舍五入原则
     */
    private static int parseDoubleToInt(double sourceDouble) {
        return (int) sourceDouble;
    }

    /***
     * @param bufferedImage 要缩放的图片对象
     * @param width_scale 要缩放到的宽度
     * @param height_scale 要缩放到的高度
     * @return 一个集合，第一个元素为宽度，第二个元素为高度
     */
    private static ArrayList<Integer> getAutoWidthAndHeight(BufferedImage bufferedImage, int width_scale,
            int height_scale) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        double scale_w = getDot2Decimal(width_scale, width);

        System.out.println("getAutoWidthAndHeight width=" + width + "scale_w=" + scale_w);
        double scale_h = getDot2Decimal(height_scale, height);
        if (scale_w < scale_h) {
            arrayList.add(parseDoubleToInt(scale_w * width));
            arrayList.add(parseDoubleToInt(scale_w * height));
        } else {
            arrayList.add(parseDoubleToInt(scale_h * width));
            arrayList.add(parseDoubleToInt(scale_h * height));
        }
        return arrayList;

    }

    /**
     * 返回两个数a/b的小数点后三位的表示
     */
    public static double getDot2Decimal(int a, int b) {
        BigDecimal bigDecimal_1 = new BigDecimal(a);
        BigDecimal bigDecimal_2 = new BigDecimal(b);
        BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2, new MathContext(4));
        Double double1 = new Double(bigDecimal_result.toString());
        System.out.println("相除后的double为：" + double1);
        return double1;
    }

}
