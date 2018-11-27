package Conversion;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
 
import java.io.File;
 
 
 
 
public class Test {
    
    /**
     * 执行转化过程
     * 
     * @param source
     *            源文件
     * @param desFileName
     *            目标文件名
     * @return 转化后的文件
     */
    public static File execute(File source, String desFileName)
            throws Exception {
        File target = new File(desFileName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(36000)); //音频比率 MP3默认是1280000
        audio.setChannels(new Integer(2));//控制参数解决音质问题
        audio.setSamplingRate(new Integer(44100));//作用同上
        EncodingAttributes attrs = new EncodingAttributes();//实例化attrs
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();//jave中Encoder实例化
        encoder.encode(source, target, attrs);
        return target;
    }
    
   public static void main(String[] args) throws Exception {
           File file = new File("/Users/a123/Desktop//1111.wav");//wav文件地址
           execute(file,  "/Users/a123/Desktop//111.mp3");//mp3文件地址
   }
}

