package com.mycom.util;

import org.apache.commons.io.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.text.*;

public final class CommonUtils{
    
    private CommonUtils(){}
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
    public static void copyFilesToDirectory(Collection<File> list, String target){
        //copy the exported file to the target directory
        File targetDir = new File(target);
        if(!targetDir.exists()){
            targetDir.mkdirs();
        }
        try{
            
            if(filesHasSameName(list)){
                File[][] groupedFiles = groupFileBySameName(list);
                //save file withou same name;

                for(File file : groupedFiles[0]){
                    if(file !=null)
                        FileUtils.copyFileToDirectory(file,targetDir);
                }

                for(int i = 1 ; i<groupedFiles.length; i++){
                    for(int j = 0; j<groupedFiles[i].length; j++){
                        if(groupedFiles[i][j] != null){
                            try{
                                FileUtils.copyFileToDirectory(groupedFiles[i][j], new File(target , groupedFiles[i][j].getParentFile().getName()));
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                for(File file : list){
                    FileUtils.copyFileToDirectory(file,targetDir);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public static File[][] groupFileBySameName(Collection<File> list){
        File[] files = list.toArray(new File[list.size()]);
        int[] group = new int[files.length];
        int max = 1;

        for(int i = 0; i<files.length-1;i++){
            if(group[i] != 0){
                continue;
            }
            for(int j=i+1; j<files.length;j++){
                if(files[i].getName().equals(files[j].getName())){
                    group[i] = group[j] = max;
                }
            }
            if(group[i]>0){
                max++;
            }
        }

        if(max == 1){
            return null;
        }

        File[][] groupedFiles= new File[max][files.length];
        int[] counter= new int[max];
        for(int i=0;i<files.length;i++){
            groupedFiles[group[i]][counter[group[i]]++]=files[i];
        }

        return groupedFiles;
        
    }
    
    private static String getFilePathNames(Collection<File> list){
        StringBuilder output = new StringBuilder();
        
        for(File f:list){
            output.append(f.getAbsolutePath().replace("C:\\Repository\\qag\\Bot\\Releases","")).append("\r\n");
        }
        return output.toString();
    }

    public static String generatePathFile(Collection<File> list, String target){
        //generate the path.txt file

        String output = getFilePathNames(list);
            
        File targetDir = new File(target);
        if(!targetDir.exists()){
            targetDir.mkdirs();
        }

        File txtFile = new File(targetDir,"path.txt");
        try{
            FileUtils.write(txtFile,output,"utf-8");
        } catch(IOException ex){
            ex.printStackTrace();
        }

        return output;
    }

    private static Date today;
    
    public static Date getToday(){
        if(today!=null)
            return today;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        return today;
    }

    public static boolean filesHasSameName(Collection<File> list){

        Set<String> fileNames = new HashSet<String>();
        for(File file :list){
            if(!fileNames.add(file.getName())){
                return true;
            }
        }
        return false;
    }

    public static void fixSectorFiles(Collection<File> list,String target,String botName) {
        if(!list.isEmpty()){
            for(File file:list){
                if(file.getName().endsWith("xml")){
                	File xmlFile = new File(target,file.getName());
                    Pattern xmlPattern = Pattern.compile("<" + botName + "[^>]*>",Pattern.CASE_INSENSITIVE);
                    String content="";
                    try {
                        content = FileUtils.readFileToString(xmlFile);
                                                
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Matcher matcher = xmlPattern.matcher(content);
                    if(matcher.find()){
                        String groupString = matcher.group();
                        try {
                            FileUtils.write(xmlFile, groupString);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                                        
                }
                else if(file.getName().endsWith("nlog")){
                	File nlogFile = new File(target,file.getName());
                    Pattern xmlPattern = Pattern.compile("<target [^>]* " + botName + "((?!</target).)*</target>|<logger [^>]*" + botName + "[^>]*>",Pattern.CASE_INSENSITIVE|Pattern.DOTALL | Pattern.COMMENTS );
                    String content="";
                    try {
                        content = FileUtils.readFileToString(nlogFile);

                        String groupString="";
                        
                        Matcher matcher = xmlPattern.matcher(content);
                        while(matcher.find()){
                        	groupString += matcher.group();
                        }
                                
                        FileUtils.write(nlogFile, groupString);
                        
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    
    
}
