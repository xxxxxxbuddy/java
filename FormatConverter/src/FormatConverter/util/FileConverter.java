package FormatConverter.util;

import it.sauronsoftware.jave.*;
import org.apache.log4j.Logger;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

/**
 * 用于文件转换的工具类 ; 包括txt转Word，word转txt，wav转mp3，png转jpg
 */
public final class FileConverter {
    private static Logger logger = Logger.getLogger(FileConverter.class);

    /**
     * png文件转jpg文件
     *
     * @param originalFile 源文件
     * @param desDirectory 目标目录
     * @param desFileName  目标文件名（不含后缀）
     * @return 返回生成的文件File
     * @throws IOException 图片读写错误
     */
    public static File pngTojpg(File originalFile, String desDirectory, String desFileName) throws IOException {
        logger.info("开始进行png文件转jpg文件...");

        if (!originalFile.getName().endsWith(".png")) {
            logger.info("传入文件不是png格式...");
            return null;
        }

        BufferedImage bufferedImage;
        File targetrFile = new File(desDirectory, desFileName + ".jpg");
        try {
            logger.info("读取图片文件...");
            bufferedImage = ImageIO.read(originalFile);

            logger.info("创建JPG图片...");
            // 创建一个和jpg图片相同大小的空白背景
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            logger.info("创建RBG图像...");
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            logger.info("写入jpg文件...");
            ImageIO.write(newBufferedImage, "jpg", targetrFile);
        } catch (IOException e) {
            logger.error("图片读写异常：" + e.getMessage());
            throw e;
        } finally {
            logger.info("png文件转jpg文件结束...");
        }

        return targetrFile;
    }

    /**
     * wav、wma文件转mp3文件
     *
     * @param originalFile 源文件
     * @param desDirectory 目标目录
     * @param desFileName  目标文件名（不含后缀）
     * @return 返回生成的文件File
     * @throws Exception 编码时的异常
     */
    public static File wavTomp3(File originalFile, String desDirectory, String desFileName) throws Exception {
        logger.info("开始进行wav、wma文件转mp3文件...");

        if (!originalFile.getName().endsWith(".wav") && !originalFile.getName().endsWith(".wma")) {
            logger.info("传入文件不是wav或wma格式...");
            return null;
        }

        File targetrFile = new File(desDirectory, desFileName + "mp3");

        logger.info("初始化音频...");
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000); // 音频比率 MP3默认是1280000
        audio.setChannels(2);// 控制参数解决音质问题
        audio.setSamplingRate(44100);// 作用同上

        logger.info("实例化音频编码器...");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try {
            logger.info("进行音频编码...");
            new Encoder().encode(originalFile, targetrFile, attrs);
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常：" + e.getMessage());
            throw e;
        } catch (InputFormatException e) {
            logger.error("输入格式异常：" + e.getMessage());
            throw e;
        } catch (EncoderException e) {
            logger.error("编码异常：" + e.getMessage());
            throw e;
        } finally {
            logger.info("wav、wma文件转mp3文件结束...");
        }

        return targetrFile;
    }

    /**
     * txt文件转docx文件
     *
     * @param originalFile 源文件
     * @param desDirectory 目标目录
     * @param desFileName  目标文件名（不含后缀）
     * @return 返回生成的文件File
     * @throws IOException word文档读写异常
     */
    public static File txtToword(File originalFile, String desDirectory, String desFileName) throws IOException {
        logger.info("开始进行txt文件转docx文件...");

        if (!originalFile.getName().endsWith(".txt")) {
            logger.info("传入文件不是txt格式...");
            return null;
        }

        File targetrFile = new File(desDirectory, desFileName + ".docx");
        try (Scanner sc = new Scanner(originalFile); FileOutputStream fout = new FileOutputStream(targetrFile)) {
            logger.info("创建word工作文档...");
            XWPFDocument document = new XWPFDocument();

            logger.info("创建word段落并写入字符...");
            while (sc.hasNextLine()) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(sc.nextLine());
            }

            logger.info("将word写入文件...");
            document.write(fout);
            logger.info("关闭word文档...");
            document.close();
        } catch (FileNotFoundException e) {
            logger.error("文件未找到：" + e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("读写异常：" + e.getMessage());
            throw e;
        } finally {
            logger.info("txt文件转docx文件结束...");
        }

        return targetrFile;
    }

    /**
     * doc或docx文件转txt文件
     *
     * @param originalFile 源文件
     * @param desDirectory 目标目录
     * @param desFileName  目标文件名（不含后缀）
     * @return 返回生成的文件File
     * @throws IOException word文档读写异常
     */
    public static File wordTotxt(File originalFile, String desDirectory, String desFileName) throws IOException {
        logger.info("开始进行word文件转txt文件...");

        if (!originalFile.getName().endsWith(".doc") && !originalFile.getName().endsWith(".docx")) {
            logger.info("传入文件不是doc或docx格式...");
            return null;
        }

        File targetrFile = new File(desDirectory, desFileName + ".txt");
        try (PrintWriter pw = new PrintWriter(targetrFile)) {
            if (originalFile.getName().endsWith(".doc")) {
                logger.info("读取doc文件的文字并写入txt文件...");
                WordExtractor ex = new WordExtractor(new FileInputStream(originalFile));
                pw.print(ex.getText());
                ex.close();
            } else if (originalFile.getName().endsWith(".docx")) {
                logger.info("读取docx文件...");
                XWPFDocument docx = new XWPFDocument(new FileInputStream(originalFile));

                logger.info("遍历段落写入txt文件...");
                for (XWPFParagraph paragraph : docx.getParagraphs())
                    pw.println(paragraph.getText());

                logger.info("关闭word文档...");
                docx.close();
            }
        } catch (FileNotFoundException e) {
            logger.error("文件未找到：" + e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("word读写错误：" + e.getMessage());
            throw e;
        } finally {
            logger.info("doc或docx文件转txt文件结束...");
        }

        return targetrFile;
    }
}
