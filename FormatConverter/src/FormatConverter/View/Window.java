package FormatConverter.View;
import FormatConverter.util.*;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.FlatBorderPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Window {
    private JFrame jf;
    private JPanel jp;
    private JButton jb1, jb2, jb3, jb4;
    private JMenuBar jmb;
    private JMenu jm;
    JMenuItem jmi;
    private static JProgressBar progressBar;
    private static String filePath = null;
    private static String fileName = null;
    /* 配置信息 */
//    private String appTitle = "app_title";
//    private String appVersion = "app_version";
    private String appProperties = "app.properties";
    private Properties settings = new Properties();
    public static boolean state = false; //转换是否成功


    private Window() {
        CreateComponents();
        BindActionListener();
        ComposeFrame();
        ReadConfig();
    }

    //创建组件
    private void CreateComponents(){
        //读取UI库
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }

        jp = new JPanel();
        jb1 = new JButton("文本文件");
        jb2 = new JButton("音频文件");
        jb3 = new JButton("图片文件");
        jb4 = new JButton("Mysql与Excel");
        jb1.setFont(new Font("华文新魏", Font.BOLD, 20));
        jb2.setFont(new Font("华文新魏", Font.BOLD, 20));
        jb3.setFont(new Font("华文新魏", Font.BOLD, 20));
        jb4.setFont(new Font("华文新魏", Font.BOLD, 20));
        jp.setLayout(null);
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(200, 20));
        jmb = new JMenuBar();
        jm = new JMenu("设置");

        jmi = new JMenuItem("输出设置");
        jm.add(jmi);
        jmb.add(jm);
        /**    设置组件位置   **/
         jmb.setBounds(0, 0, 500, 25);
        jb1.setBounds(5, 30, 200, 100);
        jb2.setBounds(220, 30, 200, 100);
        jb3.setBounds(5, 150, 200, 100);
        jb4.setBounds(220, 150, 200, 100);
        progressBar.setBounds(100, 260, 220, 20);

        /**    设置按钮尺寸	**/
        jb1.setPreferredSize(new Dimension(200, 100));
        jb2.setPreferredSize(new Dimension(200, 100));
        jb3.setPreferredSize(new Dimension(200, 100));
        jb4.setPreferredSize(new Dimension(200, 100));

        /**    设置按钮背景色	**/
        jb1.setBackground(new Color(152, 245, 255));
        jb2.setBackground(new Color(84, 255, 159));
        jb3.setBackground(new Color(238, 238, 209));
        jb4.setBackground(new Color(238, 180, 180));

    }

    //动作监听
    private class ChooseFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //打开文件选择框
            JFileChooser jfc = new JFileChooser();
            //文本文件转换
            if (e.getActionCommand().equals("txt_word")) {
                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".txt、.doc、.docx", "txt", "doc", "docx"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择txt或word文件");
                jfc.showDialog(new Label(), "选择");

                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressBar.setIndeterminate(true);
                            if ( filePath != "") {    //输出路径已设置
                                if (file.getName().endsWith(".txt")) {            //txt to docx
                                    state = TxttoWord.Excute(file, filePath.concat("\\"), file.getName().substring(0,file.getName().length()-4).concat(fileName + ".docx"));
                                } else if (file.getName().endsWith(".doc")) {        //doc to txt
                                    state = WordtoTxt.Excute(file, filePath.concat("\\"), file.getName().substring(0,file.getName().length()-4).concat(fileName + ".txt"));
                                } else {                                            //docx to txt
                                    state = WordtoTxt.Excute(file, filePath.concat("\\"), file.getName().substring(0,file.getName().length()-5).concat(fileName + ".txt"));
                                }
                                //未设置输出路径
                            } else {
                                if (file.getName().endsWith(".txt")) {            //txt to docx
                                    state = TxttoWord.Excute(file, file.getParent().concat("\\"), file.getName().substring(0,file.getName().length()-4).concat(fileName + ".docx"));
                                } else if (file.getName().endsWith(".doc")) {        //doc to txt
                                    state = WordtoTxt.Excute(file, file.getParent().concat("\\"), file.getName().substring(0,file.getName().length()-4).concat(fileName + ".txt"));
                                } else {                                            //docx to txt
                                    state = WordtoTxt.Excute(file, file.getParent().concat("\\"), file.getName().substring(0,file.getName().length()-5).concat(fileName + ".txt"));
                                }
                            }
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setIndeterminate(false);
                                    System.out.println("finish");
                                    new Toast();
                                }
                            });

                        } catch (Exception ex) {
                            progressBar.setIndeterminate(false);
                            ex.printStackTrace();
                            System.out.println("转换失败");
                            state = false;
                            new Toast();
                        }
                    }
                });
                thread.start();

            } else if (e.getActionCommand().equals("wav_mp3")) {
                //音频文件

                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".wav、.wma", "wav", "wma"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressBar.setIndeterminate(true);
                            System.out.println("start");
                            if ( filePath != "") {
                                state = WavtoMp3.Excute(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".mp3"));
                            } else {
                                state = WavtoMp3.Excute(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".mp3"));
                            }
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("finish");
                                    progressBar.setIndeterminate(false);
                                    new Toast();
                                }
                            });
                        } catch (Exception ex1) {
                            System.out.println("转换过程出现错误");
                            progressBar.setIndeterminate(false);
                            ex1.printStackTrace();
                            state = false;
                            new Toast();
                        }
                    }
                });
                thread.start();
            }

            else if (e.getActionCommand().equals("png_jpg")) {
                //图片文件

                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".png", "png"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;

                File file = jfc.getSelectedFile();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressBar.setIndeterminate(true);
                            System.out.println("start");
                            if ( filePath != "") {
                                state = PngtoJpg.excute(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".jpg"));
                            } else {
                                state = PngtoJpg.excute(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".jpg"));
                            }
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setIndeterminate(false);
                                    new Toast();
                                }
                            });
                        } catch (Exception ex1) {
                            progressBar.setIndeterminate(false);
                            System.out.println("转换过程出现错误");
                            ex1.printStackTrace();
                            state = false;
                            new Toast();
                        }
                    }
                });
                thread.start();
            } else if (e.getActionCommand().equals("database")) {
                new db();
            }

        }


    }

    //绑定动作监听
    private void BindActionListener(){

        jb1.setActionCommand("txt_word");
        jb2.setActionCommand("wav_mp3");
        jb3.setActionCommand("png_jpg");
        jb4.setActionCommand("database");
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
    }

    //组成窗体
    private void ComposeFrame(){

        /**    将组件添加到面板	**/
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        jp.add(jb4);
        jp.add(progressBar);
        jp.add(jmb);

        /**    生成窗体	**/
        jf = new JFrame("格式转换器");
        jf.setBounds(500, 300, 460, 350);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setMaximumSize(new Dimension(460, 260));
        jf.add(jp);

    }

    //读取配置
    private void ReadConfig(){
        try {
            FileInputStream in = new FileInputStream(appProperties);
            settings.load(in);

//            String title = settings.getProperty(appTitle);
//            String version = settings.getProperty(appVersion);
            filePath = settings.getProperty("filePath");
            fileName = settings.getProperty("fileName");
            in.close();
            System.out.println("加载成功");
        } catch (Exception ex2) {
            //ex2.printStackTrace();
            System.out.println("无法加载配置文件");
        }
    }

    //获取输出路径
    public static String getFilePath() {
        return filePath;
    }

    //获取输出文件名
    public static String getFileName() {
        return fileName;
    }

    //设置输出路径
    public static void setFilePath(String Path) {
        filePath = Path;
    }

    //设置输出文件名
    public static void setFileName(String Name) {
        fileName = Name;
    }

    public static void main(String[] args) {
        Window window = new Window();
    }





}
