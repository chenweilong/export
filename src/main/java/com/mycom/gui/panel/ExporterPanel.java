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
import com.mycom.gui.panel.subpanel.TableCheckBoxPanel;
import com.mycom.util.*;

import org.apache.commons.io.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ExporterPanel extends JPanel {

    private JComboBox<String> comboBox;
    private JTextField dateField;
    private JCheckBox checkBox ;
    private JCheckBox xmlcheckBox;
    private JCheckBox nlogcheckBox;
    private JCheckBox utilCheckBox;
    private JFrame textFrame;
    private JTextArea jTextAreaInsidetextFrame;
    private Exporter r20Exporter;
    private JButton btnPickdate;
    private JLabel label;
    private JLabel lblBot;
    private JComboBox<String> releaseComboBox;
    private JLabel lblBot_1;
    private TableCheckBoxPanel tablepanel;
    
    /**
     * 
     */
    public ExporterPanel(){
                
        lblBot_1 = new JLabel("bot\u7248\u672C");
                                
        releaseComboBox = new JComboBox<String>();
                                
        label = new JLabel("\u8D77\u59CB\u65E5\u671F");
                        
        dateField = new JTextField();
                        
        btnPickdate = new JButton("PickDate");
                                
        lblBot = new JLabel("bot\u5217\u8868");
                        
        // add a drop down list
        comboBox = new JComboBox<String>();
                
        JButton updateBotList = new JButton("Refresh");
        updateBotList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateBotList();
                }
            });
                
        checkBox = new JCheckBox("include cs file", true);
                
        xmlcheckBox = new JCheckBox("include xml file",false);
                
        nlogcheckBox = new JCheckBox("include nlog file", false);

        utilCheckBox = new JCheckBox("include utils", false);
        
        JButton scanFilesBtn = new JButton("scan files");
        scanFilesBtn.addActionListener(new ScanFilesListener());
                
        JButton openPath = new JButton("open path file");
        openPath.addActionListener(new OpenPathListener());
        
        tablepanel  = new TableCheckBoxPanel();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tablepanel);
        
        JButton btnExportFiles = new JButton("export files");
        btnExportFiles.addActionListener(new ExportFilesListener());

        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout
            .createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout
                .createSequentialGroup()
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.LEADING)
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addGap(91)
                        .addComponent(lblBot_1)
                        .addGap(5)
                        .addComponent(releaseComboBox, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addGap(85)
                        .addComponent(label)
                        .addGap(5)
                        .addComponent(dateField, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(btnPickdate, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addGap(91)
                        .addComponent(lblBot)
                        .addGap(5)
                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(updateBotList, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addGap(134)
                        .addComponent(scanFilesBtn, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addComponent(checkBox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(xmlcheckBox, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(nlogcheckBox)
                        .addGap(14)
                        .addComponent(utilCheckBox))
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addComponent(scrollPane, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(groupLayout
                        .createSequentialGroup()
                        .addGap(148)
                        .addComponent(btnExportFiles)
                        .addGap(18)
                        .addComponent(openPath, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))));

        groupLayout.setVerticalGroup(groupLayout
            .createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout
                .createSequentialGroup()
                .addGap(6)
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.CENTER)
                    .addComponent(lblBot_1)
                    .addComponent(releaseComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(6)
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.CENTER)
                    .addComponent(label)
                    .addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPickdate))
                .addGap(6)
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.CENTER)
                    .addComponent(lblBot)
                    .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBotList))
                .addGap(6)
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.CENTER)
                    .addComponent(nlogcheckBox)
                    .addComponent(utilCheckBox)
                    .addComponent(xmlcheckBox)
                    .addComponent(checkBox))
                .addGap(6)
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.CENTER)
                    .addComponent(scanFilesBtn))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addGroup(groupLayout
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(btnExportFiles)
                    .addComponent(openPath))
                .addGap(34)));
        setLayout(groupLayout);

        r20Exporter = new ExporterR20();

        updateBotList();
                
    }

    private void updateBotList(){
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String dateStr = dateField.getText().trim();

                    Collection<String> botChangedToday = null;

                    if("".equals(dateStr)){
                        botChangedToday = r20Exporter.getBotNamesChanged(CommonUtils.getToday());
                    } else {
                        botChangedToday = r20Exporter.getBotNamesChanged(parseDate(dateStr));
                    }

                    comboBox.removeAllItems();
                    
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

    private class ScanFilesListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String dateStr = dateField.getText().trim();
            Date startDate = null;
            if("".equals(dateStr)){
                startDate = CommonUtils.getToday();
            } else {
                startDate = parseDate(dateStr);
            }
            String botName = (String)comboBox.getSelectedItem();
            
            Collection<File> list = r20Exporter.getExportableFiles(botName,
                                                                   checkBox.isSelected(),
                                                                   xmlcheckBox.isSelected(),
                                                                   nlogcheckBox.isSelected(),
                                                                   utilCheckBox.isSelected(),
                                                                   startDate);
            tablepanel.setData(list);
            

        }
            
    }

    private class ExportFilesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String botName = (String)comboBox.getSelectedItem();

            Collection<File> list = tablepanel.getData();
            
            String result = r20Exporter.exportFiles(list,botName);
                        
            if  (result== null) {
                JOptionPane.showMessageDialog(null, "nothing exported!");
            } else {
                JOptionPane.showMessageDialog(null, result);
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
        textFrame.getContentPane().add(jTextAreaInsidetextFrame,BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowUI();
            }
        });
    }
    
    private static void createAndShowUI() {
        JFrame frame = new JFrame("CheckABunch");
        frame.getContentPane().add(new ExporterPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
