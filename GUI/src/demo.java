import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.swing.Action;
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
	private String state = "";
	JFrame jf;
	JLabel jLabel1,jLabel2;
	public static void main(String[] args){
		new demo();
	}
	public demo(){
		String notice = "Let's play a song!(only for wav file)";

		
		jf = new JFrame("Demo");
		//FlowLayout fl = new FlowLayout();
		jf.setLayout(null);						//无布局
		/* 添加菜单 */
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu1 = new JMenu("File");
		jMenu1.setMnemonic('F');				//设置热键
		JMenu jMenu2 = new JMenu("Exit");
		jMenu2.setMnemonic('E');
		jMenuBar.add(jMenu1);
		jMenuBar.add(jMenu2);
		
		jLabel1 = new JLabel(notice);
		jLabel2 = new JLabel(state);
		jLabel1.setBounds(10, 10, 200, 30);
		jLabel2.setBounds(10, 40, 200, 30);
		jf.add(jLabel1);
		jf.add(jLabel2);
		
		
		
		/* 二级菜单 */
		JMenuItem item = new JMenuItem("Import file or folder");
		jMenu1.add(item);
		
		jf.setJMenuBar(jMenuBar);
		
		JButton jb1 = new JButton("Play");
		JButton jb2 = new JButton("Pause");
		jb1.setBounds(10, 80, 100, 40);
		jb2.setBounds(140, 80, 100, 40);
//		Dimension preferredSize = new Dimension(500, 100);
//		jb1.setPreferredSize(preferredSize);
//		jf.add(jb1,BorderLayout.CENTER);
//		jf.add(jb2,BorderLayout.SOUTH);
		jf.add(jb1);
		jf.add(jb2);
		jf.setSize(400, 200);
		jf.setLocation(300,200);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
		

		jMenu2.addActionListener(this);
		jMenu2.setActionCommand("exit");
		
		item.addActionListener(this);
		item.setActionCommand("select file");
	}

	
	/* 选取文件 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String fileUrl = null;
		// TODO Auto-generated method stub
        
        
        if(e.getActionCommand().equals("select file")){
        	JFileChooser jfc=new JFileChooser();  
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
            //jfc.showDialog(new JLabel(), "选择");  
            //jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);    //只能选择文件
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Allowed File", "wav");
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
        }else if (e.getActionCommand().equals("exit")) {
			System.exit(0);
		}
        //playMusic(fileUrl);
        
        //System.out.println(jfc.getSelectedFile().getName());  
		
	}
	private void playMusic(String fileUrl) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileUrl));
			AudioFormat aif = ais.getFormat();System.out.println(aif);
			final SourceDataLine sdl;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
			sdl = (SourceDataLine) AudioSystem.getLine(info);
			sdl.open(aif);
			sdl.start();
			FloatControl fc=(FloatControl)sdl.getControl(FloatControl.Type.MASTER_GAIN);
			//value可以用来设置音量，从0-2.0
			double value=2;
			float dB = (float)
			(Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
			fc.setValue(dB);
			int nByte = 0;
			int writeByte = 0;
			final int SIZE=1024*64;
			byte[] buffer = new byte[SIZE];
			while (nByte != -1) {
				nByte = ais.read(buffer, 0, SIZE);
				sdl.write(buffer, 0, nByte);
			}
			sdl.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}



