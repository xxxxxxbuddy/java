package converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
class PngtoJpg {

  protected static boolean excute(File originalFile, String desFileName){
    BufferedImage bufferedImage;
    try {
      // 读图片文件
      bufferedImage = ImageIO.read(originalFile);
      // 创建一个和rpg图片相同大小的空白背景
      BufferedImage newBufferedImage = new BufferedImage(
              bufferedImage.getWidth(), bufferedImage.getHeight(),
              BufferedImage.TYPE_INT_RGB);
      // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
              Color.WHITE, null);
      // 写入jpg文件
      ImageIO.write(newBufferedImage, "jpg", new File(desFileName));
      System.out.println("Done");
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("转换错误");
      return false;
    }
  }
}
