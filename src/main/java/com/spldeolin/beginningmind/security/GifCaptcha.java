package com.spldeolin.beginningmind.security;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.util.gif.GifEncoder;
import lombok.Data;

/**
 * 生成Gif验证码
 *
 * @author Deolin
 */
@Component
@Data
public class GifCaptcha {

    /**
     * 随机器
     */
    public static final Random RANDOM = new Random();

    /**
     * 可选字符
     */
    public static final char CHARS[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 可选字体
     */
    private Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28);

    /**
     * 验证码随机字符个数
     */
    private int amount = 5;

    /**
     * 验证码图片宽度
     */
    private int width = 150; // 图片宽度

    /**
     * 验证码图片高度
     */
    private int height = 40;

    // 生成的字符串
    private String code = null;

    public void out(OutputStream os) {
        try {
            // 调用第三方工具类
            GifEncoder gifEncoder = new GifEncoder();
            //生成文件
            gifEncoder.start(os);
            gifEncoder.setQuality(180);
            gifEncoder.setDelay(100);
            gifEncoder.setRepeat(0);
            BufferedImage frame;
            char[] rands = alphas();
            Color fontcolor[] = new Color[amount];
            for (int i = 0; i < amount; i++) {
                fontcolor[i] = new Color(20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110));
            }
            for (int i = 0; i < amount; i++) {
                frame = graphicsImage(fontcolor, rands, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
            gifEncoder.finish();
        } finally {
            try {
                os.close();
            } catch (IOException ignored) {}
        }

    }

    /**
     * 画随机码图
     *
     * @param fontcolor 随机字体颜色
     * @param strs 字符数组
     * @param flag 透明度使用
     * @return BufferedImage
     */
    private BufferedImage graphicsImage(Color[] fontcolor, char[] strs, int flag) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //或得图形上下文
        //Graphics2D g2d=image.createGraphics();
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        //利用指定颜色填充背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        AlphaComposite ac3;
        int h = height - ((height - font.getSize()) >> 1);
        int w = width / amount;
        g2d.setFont(font);
        for (int i = 0; i < amount; i++) {
            ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i));
            g2d.setComposite(ac3);
            g2d.setColor(fontcolor[i]);
            g2d.drawOval(RANDOM.nextInt(width), RANDOM.nextInt(height), 5 + RANDOM.nextInt(10), 5 + RANDOM.nextInt(10));
            g2d.drawString(strs[i] + "", (width - (amount - i) * w) + (w - font.getSize()) + 1, h - 4);
        }
        g2d.dispose();
        return image;
    }

    /**
     * 获取透明度,从0到1,自动计算步长
     */
    private float getAlpha(int i, int j) {
        int num = i + j;
        float r = (float) 1 / amount, s = (amount + 1) * r;
        return num > amount ? (num * r - s) : num * r;
    }

    protected char[] alphas() {
        char[] cs = new char[amount];
        for (int i = 0; i < amount; i++) {
            cs[i] = CHARS[RANDOM.nextInt(CHARS.length)];
        }
        code = new String(cs);
        return cs;
    }

    /**
     * 给定范围获得随机颜色
     */
    protected Color color(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + RANDOM.nextInt(bc - fc);
        int g = fc + RANDOM.nextInt(bc - fc);
        int b = fc + RANDOM.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 获取随机字符串
     */
    public String text() {
        return code;
    }

}
