import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.util.TimerTask;
import java.util.function.IntUnaryOperator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window {
	JFrame jf;
	JPanel jp;
	JButton jb1, jb2, jb3, jb4;
	static JProgressBar progressBar;
	private static String filePath = null;

	public Window() {
		/**    新建面板及按钮组件	**/
		jp = new JPanel();
		jb1 = new JButton("txt转Word");
		jb2 = new JButton("wav转mp3");
		jb3 = new JButton("txt转Word");
		jb4 = new JButton("txt转Word");
		jp.setLayout(new FlowLayout());
		progressBar = new JProgressBar(0, 100);
		//progressBar.setValue(50);
		progressBar.setPreferredSize(new Dimension(200, 20));
		//progressBar.setStringPainted(true);

//		jb1.setBounds(5, 10, 200, 100);
//		jb2.setBounds(220, 10, 200, 100);
//		jb3.setBounds(5, 130, 200, 100);
//		jb4.setBounds(220, 130, 200, 100);
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

		/**    将组件添加到面板	**/
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		jp.add(jb4);
		jp.add(progressBar);


		/**    生成窗体	**/
		jf = new JFrame("格式转换器");
		jf.setBounds(500, 300, 460, 300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setMaximumSize(new Dimension(460, 260));
		jf.add(jp);

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
			filePath = jfc.getSelectedFile().getAbsolutePath();


			if (e.getActionCommand().equals("txt_word")) {

			} else if (e.getActionCommand().equals("wav_mp3")) {
				progressBar.setIndeterminate(true);
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							System.out.println("start");
							Wavtomp3.Excute(file, file.getName().substring(0, file.getName().length() - 3).concat("mp3"));
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
