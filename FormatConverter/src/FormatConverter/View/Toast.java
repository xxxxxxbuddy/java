package FormatConverter.View;

import org.apache.log4j.Logger;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.FlatBorderPainter;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Toast {
    private static Logger logger = Logger.getLogger(Toast.class);

    private String text = "转换完成！";

    public Toast() {

        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
            //SubstanceLookAndFeel.setSkin(new NebulaSkin());
            SubstanceLookAndFeel.setCurrentBorderPainter(new FlatBorderPainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }

        if (!Window.state) text = "转换失败";
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

        logger.info("Test");
    }

}
