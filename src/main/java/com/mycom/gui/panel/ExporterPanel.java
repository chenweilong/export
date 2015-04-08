package com.mycom.gui.panel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

import com.mycom.exporttoday.*;
import com.mycom.gui.panel.subpanel.TableCheckBoxPanel;
import com.mycom.util.*;

import org.apache.commons.io.*;
import org.apache.commons.lang3.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jdatepicker.*;
import org.jdatepicker.util.JDatePickerUtil;



public class ExporterPanel extends JPanel {

    private JComboBox<String> botlistComboBox;
    private JTextField dateField;
    private JCheckBox xmlcheckBox;
    private JCheckBox nlogcheckBox;
    private JCheckBox utilCheckBox;
    private JCheckBox qascriptCheckBox;
    private JCheckBox fixedFileCheckBox;
    private JCheckBox appconfigCheckBox;
    private JFrame textFrame;
    private JTextArea jTextAreaInsidetextFrame;
    private Exporter r20Exporter;
    private Exporter r16Exporter;
    private JButton btnPickdate;
    private JLabel label;
    private JLabel lblBot;
    private JComboBox<String> releaseComboBox;
    private JLabel lblBot_1;
    private TableCheckBoxPanel tablepanel;
    private JButton scanFilesBtn;
    private JButton updateBotListBtn;
    private JButton openPathBtn;
    private JScrollPane scrollPane;
    private JButton exportFilesBtn;
    private JDatePicker datePicker;
	
    
    /**
     * 
     */
    public ExporterPanel(){
                
        lblBot_1 = new JLabel("bot\u7248\u672C");
                                
        releaseComboBox = new JComboBox<String>();

        label = new JLabel("\u8D77\u59CB\u65E5\u671F");
                        
        // dateField = new JTextField();
                        
        // btnPickdate = new JButton("PickDate");

        JDatePickerUtil.setChineseWeekDay();
        datePicker = new JDateComponentFactory().createJDatePicker();
                                
        lblBot = new JLabel("bot\u5217\u8868");
                        
        // add a drop down list
        botlistComboBox = new JComboBox<String>();
                
        updateBotListBtn = new JButton("刷新列表");

        xmlcheckBox = new JCheckBox("include xml file",false);
                
        nlogcheckBox = new JCheckBox("include nlog file", false);

        utilCheckBox = new JCheckBox("include utils", false);
        
        qascriptCheckBox = new JCheckBox("qa script",false);
        
        fixedFileCheckBox = new JCheckBox("fixed file",true);
        
        appconfigCheckBox = new JCheckBox("appconfig",false);
        
        scanFilesBtn = new JButton("重新扫描");

        openPathBtn = new JButton("打开path");
        
        tablepanel  = new TableCheckBoxPanel();
        
        scrollPane = new JScrollPane();

        exportFilesBtn = new JButton("导出已选");

        setupLayout();

        r20Exporter = new ExporterR20();
        r16Exporter = new ExporterR16();

        setUpListeners();
        
        //updateBotListBtn();
                
    }

    private void setupLayout() {
		// TODO Auto-generated method stub
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
                         .addComponent((JComponent)datePicker,GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                         // .addComponent(dateField, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                         // .addGap(5)
                         // .addComponent(btnPickdate, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                     .addGroup(groupLayout
                         .createSequentialGroup()
                         .addGap(91)
                         .addComponent(lblBot)
                         .addGap(5)
                         .addComponent(botlistComboBox, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                         .addGap(5)
                         .addComponent(updateBotListBtn, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                     .addGroup(groupLayout
                         .createSequentialGroup()
                         .addComponent(xmlcheckBox, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                         .addPreferredGap(ComponentPlacement.UNRELATED)
                         .addComponent(nlogcheckBox)
                         .addComponent(utilCheckBox)
                         .addComponent(qascriptCheckBox)
                         .addComponent(fixedFileCheckBox)
                         .addComponent(appconfigCheckBox))
                     .addGroup(groupLayout
                         .createSequentialGroup()
                         .addComponent(scrollPane, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                     .addGroup(groupLayout
                         .createSequentialGroup()
                         .addGap(90)
                         .addComponent(scanFilesBtn, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                         .addGap(18)
                         .addComponent(exportFilesBtn, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                         .addGap(18)
                         .addComponent(openPathBtn,GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))));

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
                     .addComponent((JComponent)datePicker))
                     // .addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                     // .addComponent(btnPickdate))
                 .addGap(6)
                 .addGroup(groupLayout
                     .createParallelGroup(Alignment.CENTER)
                     .addComponent(lblBot)
                     .addComponent(botlistComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                     .addComponent(updateBotListBtn))
                 .addGap(6)
                 .addGroup(groupLayout
                     .createParallelGroup(Alignment.CENTER)
                     .addComponent(nlogcheckBox)
                     .addComponent(utilCheckBox)
                     .addComponent(xmlcheckBox)
                     .addComponent(qascriptCheckBox)
                     .addComponent(fixedFileCheckBox)
                     .addComponent(appconfigCheckBox))
                 .addPreferredGap(ComponentPlacement.UNRELATED)
                 .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                 .addGap(18)
                 .addGroup(groupLayout
                     .createParallelGroup(Alignment.BASELINE)
                     .addComponent(scanFilesBtn)
                     .addComponent(exportFilesBtn)
                     .addComponent(openPathBtn))
                 .addGap(34)));
         setLayout(groupLayout);
	}

	private void setUpListeners(){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run(){
                    releaseComboBox.addItemListener(new ReleasesEditionChangedListener());

                    releaseComboBox.addItem("R16");
                    releaseComboBox.addItem("R20");
                    releaseComboBox.setSelectedItem("R20");

                    updateBotListBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                updateBotList();
                            }
                        });

                    scrollPane.setViewportView(tablepanel);

                    scanFilesBtn.addActionListener(new ScanFilesListener());
                    openPathBtn.addActionListener(new OpenPathListener());
                    exportFilesBtn.addActionListener(new ExportFilesListener());

                    botlistComboBox.addItemListener(new BotChangedListener());
                }
            });
    }

    private Exporter getSelectedExporter(){
        String edition = (String)releaseComboBox.getSelectedItem();
        
        if(edition.equals("R16")){
            return r16Exporter;
        } else{
            return r20Exporter;
        }

    }

    private void updateBotList(){
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Date startDate = getSelectedDate();

                    Exporter exporter = getSelectedExporter();
                    Collection<String> botChangedToday = exporter.getBotNamesChanged(startDate);

                    botlistComboBox.removeAllItems();
                    
                    for (String botName : botChangedToday) {
                        botlistComboBox.addItem(botName);
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

    private Date getSelectedDate(){
        // String dateStr = dateField.getText().trim();
        Calendar cal = (Calendar)datePicker.getModel().getValue();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date startDate = cal.getTime();
        // if("".equals(dateStr)){
        //     startDate = CommonUtils.getToday();
        // } else {
        //     startDate = parseDate(dateStr);
        // }
        return startDate;    
    }

    private class ScanFilesListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            scanFiles();
        }
            
    }

    private void scanFiles(){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run(){
                    Date startDate = getSelectedDate();
            
                    String botName = (String)botlistComboBox.getSelectedItem();
            
                    Exporter exporter = getSelectedExporter();
                    Collection<File> list = exporter.getExportableFiles(botName, 
                    		startDate,
                    		xmlcheckBox.isSelected(),
                    		nlogcheckBox.isSelected(),
                    		utilCheckBox.isSelected(),
                    		qascriptCheckBox.isSelected(),
                    		appconfigCheckBox.isSelected());
                    tablepanel.setData(list);
                }
            });
    }

    private class ExportFilesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String botName = (String)botlistComboBox.getSelectedItem();

            Collection<File> list = tablepanel.getData();
            
            Exporter exporter = getSelectedExporter();
            String result = exporter.exportFiles(list,botName);
            
            String edition = (String)releaseComboBox.getSelectedItem();
            
            if(edition.equals("R20") && fixedFileCheckBox.isSelected()){
            	String target = "D:\\today\\" + CommonUtils.sdf.format(CommonUtils.getToday()) + "\\" + botName + "\\";
                CommonUtils.fixSectorFiles(list,target,botName);
            }             
                        
            if  (StringUtils.isEmpty(result)) {
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
            File f = new File("D:\\today\\" + sdf.format(new Date()) + "\\" + (String) botlistComboBox.getSelectedItem() + "\\path.txt");
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

    private class ReleasesEditionChangedListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e){
            updateBotList();
        }
    }

    private class BotChangedListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e){
            scanFiles();
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
