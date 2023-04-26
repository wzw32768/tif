package com.wzw.tiff.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tiff提取图片工具类
 *
 * @author wzw
 */
public class TiffUtil {
    private List<String> processingList = new ArrayList<>();

    /**
     * 提取tiff中第一张图片
     *
     * @param tifFile 要提取的tif图片
     * @param target  提取后的图片
     * <p>
     * 示例：
     * tifGetFirstFrame(new File("D:/images/example.tif"),new File("D:/images/a.png"));
     */
    public static void tifGetFirstFrame(File tifFile, File target) {
        if (target == null) {
            throw new RuntimeException("目标路径不正确");
        }

        target = createEmptyTargetFile(target);
        try (ImageInputStream input = ImageIO.createImageInputStream(tifFile)) {
            ImageReader reader = ImageIO.getImageReaders(input).next();
            //顺序读取，忽略元数据
            reader.setInput(input, true, false);
            target.mkdirs();
            //只读取第一张
            BufferedImage image = reader.read(0);
            ImageIO.write(image, "PNG", target);
            reader.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("tiff转换失败");
        }
    }

    /**
     * 在有同名文件的情况下重命名并创建空文件
     *
     * @param target 原文件
     * @return 修改文件名后的文件
     */
    public static synchronized File createEmptyTargetFile(File target) {
        File parentPath = target.getParentFile();
        if (target.exists()) {
            String fileName = target.getName();
            int dotPosition = fileName.indexOf(".");
            if (dotPosition != -1) {//存在"."
                //在第一个"."前加(1)
                String suffix = fileName.substring(dotPosition);
                String prefix = fileName.substring(0, dotPosition);
                while (target.exists()) {
                    prefix += "(1)";
                    target = new File(parentPath, prefix + suffix);
                }
            } else {
                //不存在"."，在文件名末尾加(1)
                while (target.exists()) {
                    target = new File(target.getAbsolutePath() + "(1)");
                }
            }
        }
        try {
            target.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 提取tiff中第一张图片
     *
     * @param filePath 原文件位置
     * @param target   修改文件名后的文件位置
     * <p>
     * 示例：
     * tifGetFirstFrame("D:/images/example.tif", "D:/images/a.png");
     */
    public static void tifGetFirstFrame(String filePath, String target) {
        tifGetFirstFrame(new File(filePath), new File(target));
    }

    /**
     * 提取tiff图片第一张并防止一张图被同时提取多次
     *
     * @param file   原文件
     * @param target 目标文件
     */
    public void tifGetFirstFrameExclusively(File file, File target) {
        synchronized (TiffUtil.class) {
            if (processingList.contains(file.getAbsolutePath())) {
                throw new RuntimeException("有进程在处理这个文件");
            }
            processingList.add(file.getAbsolutePath());
        }
        tifGetFirstFrame(file, target);
        processingList.remove(file.getAbsolutePath());
    }

    /**
     * 提取tiff中所有图片
     *
     * @param tifFile 待提取的tiff图片
     */
    public static void tifToPNG(File tifFile) {
        File target = new File("test-files", tifFile.getName());
        target.mkdirs();
        try (ImageInputStream input = ImageIO.createImageInputStream(tifFile)) {
            ImageReader reader = ImageIO.getImageReaders(input).next();
            reader.setInput(input);
            int numImages = reader.getNumImages(true);
            for (int i = 0; i < numImages; i++) {
                BufferedImage image = reader.read(i, null);
                ImageIO.write(image, "PNG", new File(target, i + ".png"));
            }
            reader.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
