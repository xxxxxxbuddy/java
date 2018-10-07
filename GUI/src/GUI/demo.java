package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.Console;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class demo extends JFrame implements ActionListener{
	private String fileName;
	static String fileUrl = null;
	static int volumeVal = 5;
	private String state = "";
	static int flag = 1;
	static boolean flag2 = true;
	JFrame jf;
	JLabel jLabel1,jLabel2,jLabel3;
	JButton jb1,jb2,jb3,jb4;
	
	
	public static void main(String[] args){
		new demo();
	}
	public demo(){
		String notice = "Let's play a song!(only for wav files)";

		
		jf = new JFrame("Demo");
		//FlowLayout fl = new FlowLayout();
		jf.setLayout(null);						//无布局
		/* 添加菜单 */
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu1 = new JMenu("File");
		jMenu1.setMnemonic('F');				//设置热键
		jMenuBar.add(jMenu1);

		/* 添加label */
		jLabel1 = new JLabel(notice);
		jLabel2 = new JLabel(state);
		jLabel3 = new JLabel("音量:" + volumeVal);
		jLabel1.setBounds(10, 10, 250, 30);
		jLabel2.setBounds(10, 40, 300, 30);
		jLabel3.setBounds(320, 40, 45, 20);
		jf.add(jLabel1);
		jf.add(jLabel2);
		jf.add(jLabel3);
		
		
		
		/* 二级菜单 */
		JMenuItem item1 = new JMenuItem("Import file");
		jMenu1.add(item1);
		JMenuItem item2 = new JMenuItem("Exit");
		jMenu1.add(item2);
		jf.setJMenuBar(jMenuBar);
		
		jb1 = new JButton("Play");
		jb2 = new JButton("Stop");
		jb3 = new JButton("+");
		jb4 = new JButton("-");
		jb1.setBounds(10, 80, 100, 40);
		jb2.setBounds(140, 80, 100, 40);
		jb3.setBounds(250, 80, 60, 40);
		jb4.setBounds(320, 80, 60, 40);
		
		jb3.setFont(new Font("Chaparral Pro Light",Font.PLAIN,20));
		jb4.setFont(new Font("Chaparral Pro Light",Font.PLAIN,20));

		/* 建立窗体 */
		jf.setIconImage(new ImageIcon("icon.png").getImage());
		jf.setTitle("MusicPlayer");
		jf.add(jb1);
		jf.add(jb2);
		jf.add(jb3);
		jf.add(jb4);
		jf.setSize(400, 200);
		jf.setLocation(300,200);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);								//使窗体大小不变
		
		
		/* 绑定事件监听 */
		item1.addActionListener(this);
		item1.setActionCommand("select file");
		item2.addActionListener(this);
		item2.setActionCommand("exit");
		
		jb1.addActionListener(this);
		jb1.setActionCommand("play");
		jb2.addActionListener(this);
		jb2.setActionCommand("stop");
		jb3.addActionListener(this);
		jb3.setActionCommand("turn up");
		jb4.addActionListener(this);
		jb4.setActionCommand("turn down");
	}

	
	/* 选取文件 */
	@Override
	public void actionPerformed(ActionEvent e) {
		playMusic pm = new playMusic(fileUrl);
		// TODO Auto-generated method stub
        
        if(e.getActionCommand().equals("select file")){
        	JFileChooser jfc=new JFileChooser();  
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );  							//只能选择文件
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".wav", "wav");	//限定格式为wav
            File filePath = new File("./");
            jfc.setCurrentDirectory(filePath);
            jfc.setFileFilter(filter);
        	int value = jfc.showOpenDialog(demo.this);
            if (value == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                if(file.isDirectory()){  
                    System.out.println("文件夹:"+file.getAbsolutePath());  
                }else if(file.isFile()){  
                    fileUrl = file.getAbsolutePath();
                    fileName = file.getName();
                    state = "Playing: " + fileName ;
                    jLabel2.setText(state);
                }  
            }
        }
        
        //			播放
        else if(e.getActionCommand().equals("play")){
    		if(fileUrl != null){
    			pm.start();
    		}

        }
        
        //			停止
        else if(e.getActionCommand().equals("stop")){
        		//pm.stopPlay();
//        		try {
//					pm.pause();
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
        	pm.interrupt();
        }
        
        //			增大音量
         else if(e.getActionCommand().equals("turn up")){
        	volumeVal = pm.turnUp();
        	jLabel3.setText("音量:" + volumeVal);
        }
        
        //			减小音量
        else if(e.getActionCommand().equals("turn down")){
        	volumeVal = pm.turnDown();
        	jLabel3.setText("音量:" + volumeVal);
        	pm.Resume();
        }
        
        //			退出
        else if (e.getActionCommand().equals("exit")) {
			System.exit(0);
		}
	}
	
}


