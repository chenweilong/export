package com.mycom.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.text.*;
import com.mycom.gui.panel.*;

public class MainFrame extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = -1245831187038866496L;

    public MainFrame() {
        //setLayout(new GridLayout(2, 1));
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("Exporter",new ExporterPanel());
        tabPane.addTab("Packer",new PackBotPanel());
        add(tabPane);
        pack();
        run();
    }
    
    private void run() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run(){
                    MainFrame j = new MainFrame();
                }
            });
    }
}
