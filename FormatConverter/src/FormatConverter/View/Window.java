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
    private static JProgressBar progressBar;
    /* setPath值为true时，生成路径被修改，需要读取配置文件中的路径信息 */
    static Boolean setPath = false;
    private static String filePath = null;
    /* 配置信息 */
    private String appTitle = "app_title";
    private String appVersion = "app_version";
    private String appProperties = "app.properties";
    private Properties settings = new Properties();
    private static boolean state = false; //转换是否成功


    private Window() {
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }
        /**    新建面板及按钮组件	**/
        jp = new JPanel();
        jb1 = new JButton("文本文件");
        jb2 = new JButton("音频文件");
        jb3 = new JButton("图片文件");
        jb4 = new JButton("数据库提取表格");
        jb1.setFont(new Font("华文新魏", Font.BOLD, 20));
        jb2.setFont(new Font("华文新魏", Font.BOLD, 20));
        jb3.setFont(new Font("华文新魏", Font.BOLD, 20));
        jb4.setFont(new Font("华文新魏", Font.BOLD, 20));
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
        jp.add(jb1, BorderLayout.SOUTH);
        jp.add(jb2, BorderLayout.SOUTH);
        jp.add(jb3, BorderLayout.SOUTH);
        jp.add(jb4, BorderLayout.SOUTH);
        jp.add(progressBar, BorderLayout.SOUTH);
        jp.add(jmb);

        /**    生成窗体	**/
        jf = new JFrame("格式转换器");
        jf.setBounds(500, 300, 460, 350);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setMaximumSize(new Dimension(460, 260));
        //jf.add(jmb);
        jf.add(jp);

        /*	读取配置 */
        try {
            FileInputStream in = new FileInputStream(appProperties);
            settings.load(in);

            String title = settings.getProperty(appTitle);
            String version = settings.getProperty(appVersion);
            if (settings.getProperty("filePath") != null) {
                filePath = settings.getProperty("filePath");
            }
            in.close();
            System.out.println(title + "\t" + version);
        } catch (Exception ex2) {
            //ex2.printStackTrace();
            System.out.println("初次加载");
        }

    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String Path) {
        filePath = Path;
    }

    public static void main(String[] args) {
        Window window = new Window();
    }

    /**
     * 动作监听
     **/
    class ChooseFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

//			try {
//				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}

            if (e.getActionCommand().equals("txt_word")) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileFilter(new FileNameExtensionFilter(".txt、.doc、.docx", "txt", "doc", "docx"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setDialogTitle("请选择txt或word文件");
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();

                System.out.println(jfc.getSelectedFile().getAbsolutePath());

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setIndeterminate(true);
                        try {
                            System.out.println("start");
                            if (setPath && filePath != null) {    //输出路径已设置
                                if (file.getName().endsWith(".txt")) {            //txt to docx
                                    state = TxttoWord.Excute(file, filePath.concat("\\"), file.getName().replace(".txt", ".docx"));
                                } else if (file.getName().endsWith(".doc")) {        //doc to txt
                                    state = WordtoTxt.Excute(file, filePath.concat("\\"), file.getName().replace(".doc", ".txt"));
                                } else {                                            //docx to txt
                                    state = WordtoTxt.Excute(file, filePath.concat("\\"), file.getName().replace(".docx", ".txt"));
                                }
                                //未设置输出路径
                            } else {
                                if (file.getName().endsWith(".txt")) {            //txt to docx
                                    state = TxttoWord.Excute(file, file.getParent().concat("\\"), file.getName().replace(".txt", ".docx"));
                                } else if (file.getName().endsWith(".doc")) {        //doc to txt
                                    state = WordtoTxt.Excute(file, file.getParent().concat("\\"), file.getName().replace(".doc", ".txt"));
                                } else {                                            //docx to txt
                                    state = WordtoTxt.Excute(file, file.getParent().concat("\\"), file.getName().replace(".docx", ".txt"));
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
                            ex.printStackTrace();
                            System.out.println("转换失败");
                        }
                    }
                });
                thread.start();

            } else if (e.getActionCommand().equals("wav_mp3")) {
                //限定选择文件的格式
                JFileChooser jfc = new JFileChooser();
                jfc.setFileFilter(new FileNameExtensionFilter(".wav、.wma", "wav", "wma"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
                System.out.println(jfc.getSelectedFile().getAbsolutePath());


                progressBar.setIndeterminate(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("start");
                            if (setPath && filePath != null) {
                                state = WavtoMp3.Excute(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 3).concat("mp3"));
                            } else {
                                state = WavtoMp3.Excute(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 3).concat("mp3"));
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
                            ex1.printStackTrace();
                            state = false;
                            new Toast();
                        }
                    }
                });
                thread.start();
            } else if (e.getActionCommand().equals("png_jpg")) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileFilter(new FileNameExtensionFilter(".png", "png"));
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new Label(), "选择");
                if (jfc.getSelectedFile() == null) return;
                File file = jfc.getSelectedFile();
                System.out.println(jfc.getSelectedFile().getAbsolutePath());

                progressBar.setIndeterminate(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("start");
                            if (setPath && filePath != null) {
                                state = PngtoJpg.excute(file, filePath.concat("\\"), file.getName().substring(0, file.getName().length() - 3).concat("jpg"));
                            } else {
                                state = PngtoJpg.excute(file, file.getParent().concat("\\"), file.getName().substring(0, file.getName().length() - 3).concat("jpg"));
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

    class ProcessRate extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        JProgressBar bar;

        public ProcessRate(JProgressBar bar) {
            this.bar = bar;
        }

        @Override
        public Void doInBackground() {
            bar.setIndeterminate(true);
            return null;
        }
    }

    class Toast {
        private String text = "转换完成！";

        public Toast() {
            if (!state) text = "转换失败";
            JLabel jlToast = new JLabel(text, JLabel.CENTER);
            JButton jbToast = new JButton("确定");
            JFrame toast = new JFrame("提示");
            jbToast.setPreferredSize(new Dimension(100, 30));
            jbToast.setBounds(20, 50, 50, 20);
            jbToast.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toast.setVisible(false);
                }
            });
            toast.setBounds(600, 600, 300, 100);
            toast.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            toast.setVisible(true);
            toast.setResizable(false);
            toast.add(jlToast, BorderLayout.CENTER);
            toast.add(jbToast, BorderLayout.SOUTH);
        }
    }

}
