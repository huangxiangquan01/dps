package cn.xqhuang.dps.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageCompressor {
    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("/Users/huangxiangquan/Downloads/CETC-2000_01.jpg"));
            int newWidth = originalImage.getWidth() / 10;
            int newHeight = originalImage.getHeight() / 10;
            Image thumbnail = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics().drawImage(thumbnail, 0, 0, null);
            ImageIO.write(bufferedImage, "jpg", new File("/Users/huangxiangquan/Desktop/thumbnail.jpg"));
            System.out.println("图片压缩完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
