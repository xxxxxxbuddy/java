package convertion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ConvertImageFile {
  public static void main(String[] args) {
    BufferedImage bufferedImage;
    try {
      // 读图片文件
      bufferedImage = ImageIO.read(new File("/Users/a123/Desktop//java.png"));
      // 创建一个和rpg图片相同大小的空白背景
      BufferedImage newBufferedImage = new BufferedImage(
          bufferedImage.getWidth(), bufferedImage.getHeight(),
          BufferedImage.TYPE_INT_RGB);
      // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
          Color.WHITE, null);
      // 写入jpg文件
      ImageIO.write(newBufferedImage, "jpg", new File("/Users/a123/Desktop//java.jpg"));
      System.out.println("Done");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
