package com.mycom.export;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;


public class Export {
    public static String botDir = "C:\\Repository\\qag\\Bot\\Releases\\R20\\";
    private static String destDir = System.getProperty("user.home") + "\\Desktop\\";

    public static String sectorName;
    public static String botName;
    static Pattern pattern;

    public static boolean checkFileExist(String regex) {
        pattern = Pattern.compile(regex + ".cs",Pattern.CASE_INSENSITIVE);
        list.clear();
        walk(new File(botDir + "APP\\Majestic.Bot.Job\\"));
                
        if(!list.isEmpty()){
                        
            sectorName = list.get(0).getParentFile().getName();
            botName = regex;
            return true;
        }
                
        return false;
    }

    private static List<File> list = new ArrayList<File>();
        
    private static List<String> copiedFile = new ArrayList<String>();

    private static List<File> walk(File inputfile) {

        if (inputfile.isDirectory()) {
            File[] files = inputfile.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File pathname) {
                        if (pathname.isDirectory()
                            && !pathname.getName().startsWith("."))
                            return true;
                        if (pattern.matcher(pathname.getName()).find())
                            return true;
                        return false;
                    }
                });

            for (File f : files) {
                if (f.isDirectory()) {
                    if (pattern.matcher(f.getName()).find()) {
                        list.add(f);
                    } else
                        walk(f);
                }

                else
                    list.add(f);
            }
        }

        return list;

    }

    public static void pack() throws IOException{
        list.clear();
        copiedFile.clear();
        pattern = Pattern.compile(botName,Pattern.CASE_INSENSITIVE);
        Export.export(Export.walk(new File(botDir)));

        //generate the master.bat if it dosen't exist
        boolean hasMaster = false;
        List<String> sqlFiles = new ArrayList<String>();
        for(String filePath:copiedFile){
            if(filePath.contains("master.bat"))
                hasMaster = true;
            else if(filePath.contains(".sql")){
                sqlFiles.add(filePath);
            }
        }
        if(!hasMaster && !sqlFiles.isEmpty()){
            //write to the database dir path
            File batFile = new File(new File(botDir + sqlFiles.get(0)).getParentFile().getAbsolutePath() + "\\master.bat");
            BufferedWriter bw = new BufferedWriter(new FileWriter(batFile));
            StringBuilder sb = new StringBuilder();
            for(String sqlFile:sqlFiles){
                sb.append("osql -E -S localhost -d JobCentral -i ").append(sqlFile.substring(sqlFile.lastIndexOf('\\')+1)).append("\r\n");
            }                       
                        
            try {
                bw.write(sb.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                if(bw!=null){
                    bw.close();
                }
            }
            export(Arrays.asList(batFile));
        }
                
                
        //copy the sector files
        pattern = Pattern.compile(sectorName + "\\.(xml|nlog)");
        list.clear();
        Export.export(walk(new File(botDir + "APP\\Majestic.Bot.Job\\")));
                
        try {
                         
            BufferedWriter bw  = new BufferedWriter(new FileWriter(destDir + botName + "\\path.txt"));
            for(String path:copiedFile){
                bw.write("R20" + path + "\r\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
                
        //fixed the sector files
        fixSectorFile();
    }

    private static void fixSectorFile() {
        if(!list.isEmpty()){
            for(File file:list){
                File copiedFile = new File(destDir + botName + file.getAbsolutePath().substring(botDir.length()-1));
                if(copiedFile.getName().endsWith("xml")){
                    Pattern xmlPattern = Pattern.compile("<" + botName + "[^>]*>",Pattern.CASE_INSENSITIVE);
                    String content="";
                    try {
                        content = readFileAsString(copiedFile.getAbsolutePath());
                                                
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Matcher matcher = xmlPattern.matcher(content);
                    if(matcher.find()){
                        String groupString = matcher.group();
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(copiedFile.getAbsoluteFile()));
                            bw.write(groupString.toCharArray());
                            bw.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                                        
                }
                else if(copiedFile.getName().endsWith("nlog")){
                    Pattern xmlPattern = Pattern.compile("<target [^>]* " + botName + "((?!</target).)*</target>|<logger [^>]*" + botName + "[^>]*>",Pattern.CASE_INSENSITIVE|Pattern.DOTALL | Pattern.COMMENTS );
                    String content="";
                    try {
                        content = readFileAsString(copiedFile.getAbsolutePath());

                        BufferedWriter bw = new BufferedWriter(new FileWriter(copiedFile.getAbsoluteFile()));
                        Matcher matcher = xmlPattern.matcher(content);
                        while(matcher.find()){
                            String groupString = matcher.group();

                            bw.write(groupString.toCharArray());
                            bw.write("\r\n");

                        }
                                                
                        bw.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
        
    private static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                                                   new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    private static void export(List<File> files) {
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
                try {
                    copyFile(file,new File(destDir + botName + file.getAbsolutePath().substring(botDir.length()-1)));
                    copiedFile.add(file.getAbsolutePath().substring(botDir.length()-1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // if this is a category, we should copy all the file under this category
                export(Arrays.asList(file.listFiles()));
            }
        }
    }

    private static void copyFile(File sourceFile, File destFile)
        throws IOException {

        destFile.getParentFile().mkdirs();
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
