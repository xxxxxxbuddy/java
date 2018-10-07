package GUI;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/*	读取wav文件	*/
public class playMusic extends Thread {
	private String fileUrl;
	static boolean flag = true;
	public playMusic(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	static double value = 1;
	static float dB;
	static FloatControl fc;
	static int volumeValue = 5 ;
	static SourceDataLine auline = null;
	
	
	public void run() {
		AudioInputStream audioInputStream = null;
		 
		// 使用AudioSystem来获取音频的音频输入流
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(fileUrl));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		// 用AudioFormat来获取AudioInputStream的格式
 
 
		AudioFormat format = audioInputStream.getFormat();
 
		// 源数据行SoureDataLine是可以写入数据的数据行
		//SourceDataLine auline = null;
 
		// 获取受数据行支持的音频格式DataLine.info
 
 
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
		// 获得与指定info类型相匹配的行
		while(!Thread.currentThread().isInterrupted()){
			try {
				auline = (SourceDataLine) AudioSystem.getLine(info);
	 
				// 打开具有指定格式的行，这样可使行获得所有所需系统资源并变得可操作
	 
	 
				auline.open();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	 
			// 允许某一个数据行执行数据i/o
			auline.start();
	 
			// 写出数据
			fc=(FloatControl)auline.getControl(FloatControl.Type.MASTER_GAIN);
			//value可以用来设置音量，从0-2.0

			dB = (float)(Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
			fc.setValue(dB);
	 
			int nBytesRead = 0;
			
			byte[] abData = new byte[8];
	 
			// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
			try {
				while ((nBytesRead != -1) && (flag)) {
					//System.out.println(flag);
					fc.setValue(dB);
					nBytesRead = audioInputStream.read(abData, 0, abData.length);
	 
					// 通过此源数据行将数据写入混频器
	 
	 
					if (nBytesRead >= 0)
						auline.write(abData, 0, nBytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
	 
			} finally {
				auline.drain();
				auline.close();
			}
			return;
		}
	}

	
	public void stopPlay(){
		flag = false;
	}

	
	public int turnUp(){
		if(value <= 1.9 && value > 1.7){
			value = 2;	
			volumeValue = 10;
		}else if(value == 2){
			value = 2;
			volumeValue = 10;
		}else{
			value += 0.2;
			volumeValue += 1;
		}

		dB = (float)(Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
//		System.out.println(value);
//		System.out.println(dB);
		return volumeValue;
	}
	public int turnDown(){
		if(value > 0.1 && value <= 0.3){
			value = 0;	
			volumeValue = 0;
		}else if(value == 0){
			value = 0;
			volumeValue = 0;
		}else{
			value -= 0.2;
			volumeValue -= 1;
		}
		dB = (float)(Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
//		System.out.println(value);
//		System.out.println(dB);
		return volumeValue;
	}
	public void pause() throws InterruptedException{
		auline.close();
	}
	public void Resume(){
		auline.drain();
	}
}

