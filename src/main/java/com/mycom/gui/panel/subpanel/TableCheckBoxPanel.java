package com.mycom.gui.panel.subpanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/** @see http://stackoverflow.com/questions/4526779 */
public class TableCheckBoxPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int CHECK_COL = 1;

    private static final String[] HEADCOLUMNS = {"Number", "CheckBox"};
    private DataModel dataModel = new DataModel(null, HEADCOLUMNS);

    private JTable table;
    
    public TableCheckBoxPanel() {
        super(new BorderLayout());
        
        table = new JTable(dataModel);
        
        this.add(new JScrollPane(table));
//        this.add(new ControlPanel(), BorderLayout.SOUTH);
        table.setPreferredScrollableViewportSize(new Dimension(250, 175));
        table.getColumnModel().getColumn(1).setMinWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        
    }
    
    public void setData(Collection<File> list){

        if(list == null || list.size() == 0){
            dataModel.setDataVector(null, HEADCOLUMNS);
            return;
        }
        
        File[] alist = list.toArray(new File[list.size()]);

        Object[][] datas = new Object[list.size()][2];
        

        for(int i=0;i<alist.length; i++){
            datas[i] = new Object[]{alist[i], Boolean.TRUE};
        }

    	dataModel.setDataVector(datas, HEADCOLUMNS);

        table.getColumnModel().getColumn(1).setMinWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
    	

    }

    public Collection<File> getData(){
    
    	
    	Collection<File> list = new ArrayList<File>();
        
        for (int i = 0; i < dataModel.getRowCount(); i++) {

            	if(((Boolean)dataModel.getValueAt(i,1))){
            		
                    list.add((File)dataModel.getValueAt(i, 0));
            	}

        }
        return list;

    }
    public class DataModel extends DefaultTableModel {

        public DataModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == CHECK_COL) {
                return getValueAt(0, CHECK_COL).getClass();
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == CHECK_COL;
        }
    }

    public class ControlPanel extends JPanel {

        public ControlPanel() {
            this.add(new JLabel("Selection:"));
            this.add(new JButton(new SelectionAction("Clear", false)));
            this.add(new JButton(new SelectionAction("Check", true)));
        }
    }
    
    private class SelectionAction extends AbstractAction {

        boolean value;

        public SelectionAction(String name, boolean value) {
            super(name);
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < dataModel.getRowCount(); i++) {
            	dataModel.setValueAt(value, i, CHECK_COL);

            }

        }
    }

    private static void createAndShowUI(JPanel p) {
    	
        JFrame frame = new JFrame("CheckABunch");
        frame.add(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	final TableCheckBoxPanel p = new TableCheckBoxPanel();
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                createAndShowUI(p);
                p.setData(Arrays.asList(new File[]{new File("d:/adas")}));
                
                Collection<File> a = p.getData();
                for(Object i : a){
                    System.out.println(i);
                
                }
            }
        });
        
    }
}
