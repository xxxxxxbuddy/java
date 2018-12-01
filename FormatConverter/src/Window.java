import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.TimerTask;
import java.util.function.IntUnaryOperator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window {
	JFrame jf;
	JPanel jp;
	JButton jb1, jb2, jb3, jb4;
	JMenuBar jmb;
	JMenu jm;
	static JProgressBar progressBar;
	public static Boolean setPath = false;
	public static String filePath = null;
	String appTitle = "app_title";
	String appVersion = "app_version";
	String appProperties = "app.properties";
	Properties settings = new Properties();

	public Window() {
		/**    新建面板及按钮组件	**/
		jp = new JPanel();
		jb1 = new JButton("txt转Word");
		jb2 = new JButton("wav转mp3");
		jb3 = new JButton("txt转Word");
		jb4 = new JButton("txt转Word");
		//jp.setLayout(new FlowLayout());
		jp.setLayout(null);
		progressBar = new JProgressBar(0, 100);
		progressBar.setPreferredSize(new Dimension(200, 20));
		jmb = new JMenuBar();
		jm = new JMenu("设置");
		//jm.setAccelerator(new KeyStroke("k"));

		JMenuItem jmi = new JMenuItem("输出设置");
		jm.add(jmi);
		jmb.add(jm);
		jmb.setBounds(0,0,500,25);
		jb1.setBounds(5, 30, 200, 100);
		jb2.setBounds(220, 30, 200, 100);
		jb3.setBounds(5, 150, 200, 100);
		jb4.setBounds(220, 150, 200, 100);
		progressBar.setBounds(100,260,220,20);
		/**    设置按钮尺寸	**/
		jb1.setPreferredSize(new Dimension(200, 100));
		jb2.setPreferredSize(new Dimension(200, 100));
		jb3.setPreferredSize(new Dimension(200, 100));
		jb4.setPreferredSize(new Dimension(200, 100));
		//jb1.setIcon(new ImageIcon("队徽.jpg"));
		//jb1.setMargin(new Insets(0, 0, 0, 0));
		/**    设置按钮背景色	**/
		jb1.setBackground(new Color(152, 245, 255));
		jb2.setBackground(new Color(84, 255, 159));
		jb3.setBackground(new Color(238, 238, 209));
		jb4.setBackground(new Color(238, 180, 180));
		jb1.setActionCommand("txt_word");
		jb2.setActionCommand("wav_mp3");
		jb3.setActionCommand("png_jpg");
		jb4.setActionCommand("database");

		/**    添加动作监听	**/
		ChooseFile cf1 = new ChooseFile();
		ChooseFile cf2 = new ChooseFile();
		ChooseFile cf3 = new ChooseFile();
		ChooseFile cf4 = new ChooseFile();
		jb1.addActionListener(cf1);
		jb2.addActionListener(cf2);
		jb3.addActionListener(cf3);
		jb4.addActionListener(cf4);
		jmi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Setting();
			}
		});

		/**    将组件添加到面板	**/
		jp.add(jb1,BorderLayout.SOUTH);
		jp.add(jb2,BorderLayout.SOUTH);
		jp.add(jb3,BorderLayout.SOUTH);
		jp.add(jb4,BorderLayout.SOUTH);
		jp.add(progressBar,BorderLayout.SOUTH);
		jp.add(jmb);

		/**    生成窗体	**/
		jf = new JFrame("格式转换器");
		jf.setBounds(500, 300, 460, 350);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setMaximumSize(new Dimension(460, 260));
		//jf.add(jmb);
		jf.add(jp);

		/*	读取配置 */
		try{
			FileInputStream in = new FileInputStream(appProperties);
			settings.load(in);

			String title = settings.getProperty(appTitle);
			String version = settings.getProperty(appVersion);
			if(settings.getProperty("filePath")!=null){
				filePath = settings.getProperty("filePath");
			}
			in.close();
			System.out.println(title + "\t" + version);
		}catch(Exception ex2){
			//ex2.printStackTrace();
			System.out.println("初次加载");
		}

	}

	public static void main(String[] args) {
		new Window();
	}

	/**
	 * 动作监听
	 **/
	class ChooseFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			JFileChooser jfc = new JFileChooser();// TODO Auto-generated method stub
			jfc.setFileFilter(new FileNameExtensionFilter(".wav、.wma", "wav", "wma"));
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(new Label(), "选择");
			File file = jfc.getSelectedFile();
			System.out.println(jfc.getSelectedFile().getAbsolutePath());
			//filePath = jfc.getSelectedFile().getAbsolutePath();


			if (e.getActionCommand().equals("txt_word")) {

			} else if (e.getActionCommand().equals("wav_mp3")) {
				progressBar.setIndeterminate(true);
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							System.out.println("start");
							if (setPath && filePath!=null ){
								Wavtomp3.Excute(file,filePath.concat("\\" + file.getName().substring(0, file.getName().length() - 3).concat("mp3")));
							}else{
								Wavtomp3.Excute(file, file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 3).concat("mp3"));
							}
							SwingUtilities.invokeAndWait(new Runnable() {
								@Override
								public void run() {
									System.out.println("finish");
									progressBar.setIndeterminate(false);
								}
							});
						}catch(Exception ex1){
							System.out.println("转换过程出现错误");
							ex1.printStackTrace();
						}
					}
				});
				thread.start();
			} else if (e.getActionCommand().equals("png_jpg")) {

			} else if (e.getActionCommand().equals("database")) {

			}

		}


	}

	//	class ProcessRate extends Thread{
//		JProgressBar bar;
//		public ProcessRate(){
//			bar = progressBar;
//		}
//		public void run(){
//			bar.setIndeterminate(true);
//		}
//	}
	class ProcessRate extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		JProgressBar bar;
		public ProcessRate(JProgressBar bar){
			this.bar = bar;
		}
		@Override
		public Void doInBackground() {
			bar.setIndeterminate(true);
			return null;
		}
	}

}
