package FormatConverter.View;

import FormatConverter.util.*;
import com.mysql.cj.log.Log;
import com.sun.jdi.InvocationException;
import org.apache.log4j.Logger;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    private static Logger logger = Logger.getLogger(Window.class);

    private Window() {
        InitComponents();
        BindActionListener();
        ComposeFrame();
        ReadConfig();
    }

    //创建组件
    private void InitComponents() {
        logger.info("初始化窗体组件...");
        //读取UI库
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            logger.info("UI库加载失败:" + e.getMessage());
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
        private Logger logger = Logger.getLogger(ChooseFile.class);
        @Override
        public void actionPerformed(ActionEvent e) {
            logger.info("声明动作监听");
            //打开文件选择框
            JFileChooser jfc = new JFileChooser();
            //文本文件转换
            if (e.getActionCommand().equals("txt_word")) {
                logger.info("文本文件转换");
                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".txt、.doc、.docx", "txt", "doc", "docx"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择txt或word文件");
                jfc.showDialog(new Label(), "选择");

                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
                logger.info("选择源文件:" + file.getAbsolutePath());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File desFile = null;
                        try {
                            progressBar.setIndeterminate(true);
                            if (filePath != "") {    //输出路径已设置
                                if (file.getName().endsWith(".txt")) {            //txt to docx
                                    desFile = FileConverter.txtToword(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".docx"));
                                    state = desFile.exists();
                                } else if (file.getName().endsWith(".doc")) {        //doc to txt
                                   desFile = FileConverter.wordTotxt(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".txt"));
                                    state = desFile.exists();
                                } else {                                            //docx to txt
                                    desFile = FileConverter.wordTotxt(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 5).concat(fileName + ".txt"));
                                    state = desFile.exists();
                                }
                                logger.info("转换完成，生成文件:" + desFile.getAbsolutePath());
                                //未设置输出路径
                            } else {
                                if (file.getName().endsWith(".txt")) {            //txt to docx
                                    desFile = FileConverter.txtToword(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".docx"));
                                    state = desFile.exists();
                                } else if (file.getName().endsWith(".doc")) {        //doc to txt
                                    desFile = FileConverter.wordTotxt(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".txt"));
                                    state = desFile.exists();
                                } else {                                            //docx to txt
                                    desFile = FileConverter.wordTotxt(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 5).concat(fileName + ".txt"));
                                    state = desFile.exists();
                                }
                                logger.info("转换完成，生成文件:" + desFile.getAbsolutePath());
                            }
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setIndeterminate(false);
                                    new Toast();
                                }
                            });

                        } catch (Exception e) {
                            logger.error("转换失败:" + e.getMessage());
                            progressBar.setIndeterminate(false);
                            state = false;
                            new Toast();
                        }
                    }
                });
                thread.start();

            } else if (e.getActionCommand().equals("wav_mp3")) {
                //音频文件
                logger.info("音频文件转换");
                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".wav、.wma", "wav", "wma"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择wav或wma文件");
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
                logger.info("选择源文件:" + file.getAbsolutePath());
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File desFile = null;
                        try {
                            progressBar.setIndeterminate(true);
                            if (filePath != "") {
                                desFile = FileConverter.wavTomp3(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".mp3"));
                                state = desFile.exists();
                                logger.info("转换完成，生成文件:" + desFile.getAbsolutePath());
                            } else {
                                desFile = FileConverter.wavTomp3(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".mp3"));
                                state = desFile.exists();
                                logger.info("转换完成，生成文件:" + desFile.getAbsolutePath());
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
                            ex1.printStackTrace();
                            state = false;
                            new Toast();
                            logger.error("转换失败:" + ex1.getMessage());
                        }
                    }
                });
                thread.start();
            } else if (e.getActionCommand().equals("png_jpg")) {
                //图片文件
                logger.info("图片文件转换");
                //限定选取文件格式
                jfc.setFileFilter(new FileNameExtensionFilter(".png", "png"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择png文件");
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;

                File file = jfc.getSelectedFile();
                logger.info("选择源文件:" + file.getAbsolutePath());
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File desFile = null;
                        try {
                            progressBar.setIndeterminate(true);
                            if (!filePath .equals("") ) {
                                desFile = FileConverter.pngTojpg(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".jpg"));
                                state = desFile.exists();
                            } else {
                                desFile = FileConverter.pngTojpg(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 4).concat(fileName + ".jpg"));
                                state = desFile.exists();
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
                            state = false;
                            new Toast();
                            logger.error("转换失败:" + ex1.getMessage());
                        }
                    }
                });
                thread.start();
            } else if (e.getActionCommand().equals("database")) {
                logger.info("操作数据库");
                new db();
            }

        }


    }

    //绑定动作监听
    private void BindActionListener() {
        logger.info("绑定动作监听...");
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
    private void ComposeFrame() {
        logger.info("组成窗体...");
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
    private void ReadConfig() {
        logger.info("读取配置信息...");
        try {
            FileInputStream in = new FileInputStream(appProperties);
            settings.load(in);

//            String title = settings.getProperty(appTitle);
//            String version = settings.getProperty(appVersion);
            filePath = settings.getProperty("filePath");
            fileName = settings.getProperty("fileName");
            in.close();
            logger.info("加载完成!");
        } catch (Exception ex2) {
            //ex2.printStackTrace();
            logger.info("配置文件加载失败");
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
