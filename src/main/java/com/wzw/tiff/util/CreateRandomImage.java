package com.wzw.tiff.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
/**
 * 创建随机图片工具类
 * @author wzw
 */
public class CreateRandomImage {
    public static void main(String[] args) throws Exception {
        generatePicWithSize(40, "D:/images/13.bmp");
//        for (int i = 0; i < 100; i++) {
//            generatePicWithSize(2, "D:/images/" + i + ".bmp");
//        }
    }

    /**
     * 生成随即图片
     * @param width 宽度（像素）
     * @param height 高度（像素）
     * @param path 输出路径
     */
    public static void generatePic(int width, int height, String path) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        Random random = new Random();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                g2.setPaint(color);
                g2.drawRect(x, y, 1, 1);
            }
        }
        try {
            ImageIO.write(bi, "bmp", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 以指定大小生成随机图片
     * @param size 要生成的随即图片的大小（单位MB）
     * @param path 输出路径
     */
    public static void generatePicWithSize(int size, String path) {
        long pixels = size * 1024 * 1024 / 3;
        int a = (int) Math.sqrt(pixels);
        generatePic(a, a, path);
    }
}
