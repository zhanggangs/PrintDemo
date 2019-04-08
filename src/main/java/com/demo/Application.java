package com.demo;

import com.demo.gui.Gui;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    SpringApplication.run(Application.class, args);
                    Gui.lblNewLabel.setText("<html><body><p align=\"center\">--打印服务已启动--</p></body></html>");
                    Gui.lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 30));
                    Gui.lblNewLabel.setForeground(new Color(60, 179, 113));
                    Gui.lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    Gui.lblNewLabel.setBounds(43, 67, 359, 108);
                    Gui.frame.getContentPane().add(Gui.lblNewLabel);
                } catch (Exception e) {
                    Gui.lblNewLabel.setText("<html><body><p align=\"center\">--打印服务启动失败--</p></body></html>");
                    Gui.lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 30));
                    Gui.lblNewLabel.setForeground(new Color(255, 0, 0));
                    Gui.lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    Gui.lblNewLabel.setBounds(43, 67, 359, 108);
                    Gui.frame.getContentPane().add(Gui.lblNewLabel);
                }
                Gui.createAndShowGUI();
            }
        });
    }
}
