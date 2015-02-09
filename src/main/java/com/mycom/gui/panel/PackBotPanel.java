package com.mycom.gui.panel;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;
import com.mycom.export.*;


public class PackBotPanel extends JPanel{


    private JTextField path;
    private JLabel label;
    private JTextField text;
    private JLabel messageLabel;
    private JButton packBtn;
    private String sectorName = "";
    private String botName = null;
    
    public PackBotPanel(){

        setLayout(new FlowLayout());

        path = new JTextField(Export.botDir);
        path.setColumns(30);
        add(path);

        label = new JLabel("bot name");
        add(label);

        text = new JTextField();
        text.setColumns(10);
        add(text);

        packBtn = new JButton("decect");
        packBtn.addActionListener(detect);
        add(packBtn);

        messageLabel = new JLabel();
        add(messageLabel);

    }

    ActionListener detect = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (text.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please input a bot name");
                    return;
                }

                Export.botDir = path.getText();

                String fileName = text.getText().trim();
                if (fileName.endsWith(".cs")) {
                    fileName = fileName.substring(0, fileName.length() - 3);
                }

                // check the file, and save the fileName to the Export's field.
                if(Export.checkFileExist(fileName)){
                    sectorName = Export.sectorName;
                }
                botName = fileName;
                if (sectorName != null) {
                    packBtn.removeActionListener(this);
                    packBtn.setText("start pack");
                    packBtn.addActionListener(pack);
                    packBtn.setBackground(Color.green);

                    // JLabel label2 = new JLabel("sector: " + sectorName);
                    // MainFrame.this.add(label2);
                    messageLabel.setText("sector: " + sectorName);

                } else {
                    JOptionPane.showMessageDialog(null, "Bot dosen't exist");
                }
            }
        };

    ActionListener pack = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (text.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "regex can't be empty");
                    return;
                }

                try {
                    Export.pack();
                    // MainFrame.this.add(new JLabel("finished"));
                    // MainFrame.this.validate();
                    messageLabel.setText("finished");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, e1.toString());

                }
            }
        };

}
