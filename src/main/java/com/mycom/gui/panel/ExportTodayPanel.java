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
import com.mycom.exporttoday.*;
import org.apache.commons.io.*;

public class ExportTodayPanel extends JPanel {

    private JComboBox comboBox;
    private JTextField dateField;
    private JCheckBox checkBox ;
    private JCheckBox xmlcheckBox;
    private JCheckBox nlogcheckBox;
    private JCheckBox utilCheckBox;
    private JFrame textFrame;
    private JTextArea jTextAreaInsidetextFrame;
    
    public ExportTodayPanel(){

        // set p2

        setLayout(new FlowLayout());

        // add a drop down list
        comboBox = new JComboBox();
        // comboBox.setSize(100,20);
        add(comboBox);

        dateField = new JTextField(8);
        add(dateField);
        
        checkBox = new JCheckBox("include cs file", true);
        add(checkBox);
        
        xmlcheckBox = new JCheckBox("include xml file",false);
        add(xmlcheckBox);
        
        nlogcheckBox = new JCheckBox("include nlog file", false);
        add(nlogcheckBox);

        utilCheckBox = new JCheckBox("include utils", false);
        add(utilCheckBox);

        JButton extractTodayBtn = new JButton("extract bots");
        extractTodayBtn.addActionListener(new ExtractTodayButtonListener());
        add(extractTodayBtn);

        JButton openPath = new JButton("open path file");
        openPath.addActionListener(new OpenPathListener());
        add(openPath);
                
        JButton updateBotList = new JButton("refresh");
        updateBotList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateBotList();
                }
            });
        add(updateBotList);

        updateBotList();
    }

    private void updateBotList(){
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    comboBox.removeAllItems();
                    List<String> botChangedToday = ExportToday.getBotNamesChangedToday();
                    for (String botName : botChangedToday) {
                        comboBox.addItem(botName);
                    }
                                
                }
            });
    }

    private Date parseDate(String inputStr){
        SimpleDateFormat sdf = null;
        if(inputStr.contains("-")){
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        else{
            sdf = new SimpleDateFormat("yyyyMMdd");
        }
        try{
            return sdf.parse(inputStr);
        } catch( ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    private class ExtractTodayButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String dateStr = dateField.getText().trim();
            String list =null;
            if("".equals(dateStr)){
                list = ExportToday.exportFiles((String) comboBox.getSelectedItem(),
                                               checkBox.isSelected(),
                                               xmlcheckBox.isSelected(),
                                               nlogcheckBox.isSelected(),
                                               utilCheckBox.isSelected(),
                                               ExportToday.getToday());
            } else {
                list = ExportToday.exportFiles((String) comboBox.getSelectedItem(),
                                               checkBox.isSelected(),
                                               xmlcheckBox.isSelected(),
                                               nlogcheckBox.isSelected(),
                                               utilCheckBox.isSelected(),
                                               parseDate(dateStr));
            }

            if (list == null) {
                JOptionPane.showMessageDialog(null, "nothing exported!");
            } else {
                JOptionPane.showMessageDialog(null, list);
            }
        }
            
    }

    private class OpenPathListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            File f = new File("D:\\today\\" + sdf.format(new Date()) + "\\" + (String) comboBox.getSelectedItem() + "\\path.txt");
            String text = "";
            if(f.exists()){
                try{
                    text = FileUtils.readFileToString(f);
                }
                catch(IOException ex){
                    ex.printStackTrace();
                    return;
                }
            } else{
                JOptionPane.showMessageDialog(null, "path.txt don't exist!");
                return;
            }
                
            if(textFrame == null){
                initTextFrame();
            }
            
            jTextAreaInsidetextFrame.setText(text);
            textFrame.setVisible(true);
        }
    }

    private void initTextFrame(){
        textFrame = new JFrame();
        textFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        textFrame.setSize(600, 600);
        jTextAreaInsidetextFrame = new JTextArea();
        textFrame.add(jTextAreaInsidetextFrame,BorderLayout.CENTER);
    }
}
