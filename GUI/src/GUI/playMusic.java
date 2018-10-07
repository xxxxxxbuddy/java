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

/*	��ȡwav�ļ�	*/
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
		 
		// ʹ��AudioSystem����ȡ��Ƶ����Ƶ������
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(fileUrl));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		// ��AudioFormat����ȡAudioInputStream�ĸ�ʽ
 
 
		AudioFormat format = audioInputStream.getFormat();
 
		// Դ������SoureDataLine�ǿ���д�����ݵ�������
		//SourceDataLine auline = null;
 
		// ��ȡ��������֧�ֵ���Ƶ��ʽDataLine.info
 
 
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
		// �����ָ��info������ƥ�����
		while(!Thread.currentThread().isInterrupted()){
			try {
				auline = (SourceDataLine) AudioSystem.getLine(info);
	 
				// �򿪾���ָ����ʽ���У�������ʹ�л����������ϵͳ��Դ����ÿɲ���
	 
	 
				auline.open();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	 
			// ����ĳһ��������ִ������i/o
			auline.start();
	 
			// д������
			fc=(FloatControl)auline.getControl(FloatControl.Type.MASTER_GAIN);
			//value��������������������0-2.0

			dB = (float)(Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
			fc.setValue(dB);
	 
			int nBytesRead = 0;
			
			byte[] abData = new byte[8];
	 
			// ����Ƶ����ȡָ������������������ֽڣ����������������ֽ������С�
			try {
				while ((nBytesRead != -1) && (flag)) {
					//System.out.println(flag);
					fc.setValue(dB);
					nBytesRead = audioInputStream.read(abData, 0, abData.length);
	 
					// ͨ����Դ�����н�����д���Ƶ��
	 
	 
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

