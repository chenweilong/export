package com.mycom.exporttoday;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import com.mycom.filefilter.*;
import org.apache.commons.lang3.*;

public class ExporterR20 implements Exporter {
    public static final String R20Dir = "C:\\Repository\\qag\\Bot\\Releases\\R20\\";
    
    public static final boolean DEBUG = true;
    
    public static Properties botRealName;
    private  Collection<File> allBotFileList;
    private  IOFileFilter excludedDirFilter = new NotFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties"}));

    //load the properties file
    static {
        try{
            botRealName = new Properties();
            botRealName.load(ExportToday.class.getResourceAsStream("/aliasname.properties"));
        }
        catch(IOException e){
            System.out.println("-----------failue-------------");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public  Collection<File> getAllBotFiles(){
                
        return FileUtils.listFiles(new File(R20Dir, "APP\\Majestic.Bot.Job"),
                                   new SuffixFileFilter(".cs"),
                                   new NotFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties","RegexFiles","Config"})));

    }
            
    @Override
    public List<String> getBotNamesChanged(Date startDate){
        if (startDate == null)
            startDate = CommonUtils.getToday();

        List<String> botNameList = new LinkedList<String>();

        List<File> regexList = null;
        Collection<File> files = getFilesChangedInDir("APP\\Majestic.Bot.Job",new SuffixFileFilter(new String[]{".cs",".regex"}),startDate);
        files.addAll(getFilesChangedInDir("APP\\Majestic.Bot.Job_Browser",new SuffixFileFilter(".cs")),startDate);
                
        for(File file:files){
            if(file.getName().toLowerCase().contains(".cs") ){
                botNameList.add(getBotRealName(file));
            } else if(file.getName().toLowerCase().contains(".regex")){
                if(regexList == null){
                    regexList = new ArrayList<File>();
                }
                regexList.add(file);
            }
        }

        
        if(regexList == null){
            return botNameList;
        }

        if(allBotFileList == null)
            allBotFileList = getAllBotFiles();


        Set<String> botnameset = new HashSet<String>();
        
        //add the regex file's corresponding bot file name to the hashset.
        for(File regexFile : regexList){
            for(File botfile:allBotFileList){
                String botRealName = getBotRealName(botfile);
                if(regexFile.getName().toLowerCase().contains(botRealName.toLowerCase())){
                    botnameset.add(botRealName);
                    break;
                }
            }
        }
        
        botNameList.addAll(botnameset);

        return botNameList;
    }

    private String getBotRealName(File file){
        String name = file.getName();
        if(botRealName.containsKey(name)){
            return botRealName.getProperty(name);
        } else{
            return name.substring(0,name.length() -2 );
        }
    }

    private Collection<File> getFilesChangedInDir(String targetdir, IOFileFilter filter, Date date){
                
        //check file modified today
        Collection<File> list = FileUtils.listFiles(new File(R20Dir,targetdir),
                                                    FileFilterUtils.and(filter,new AgeFileFilter(date,false)),
                                                    excludedDirFilter);
        return list;
    }
    
    private Collection<File> getExportableFiles(String botName,
                                                boolean includeBot,
                                                boolean includeSection,
                                                boolean includeNlog,
                                                boolean includeUtil,
                                                Date startDate){

        Collection<File> list = getFilesChangedInDir("",
                                                     new AndFileFilter(new PathNameRegexFileFilter(botName) ,
                                                                       new NotFileFilter(new SuffixFileFilter(".csproj",IOCase.INSENSITIVE))),
                                                     startDate);

        if (includeUtil) {
            list.addAll(getFilesChangedInDir("Framework\\Util",new SuffixFileFilter(".cs",IOCase.INSENSITIVE),startDate));
        }
        
        //add the section file
        if(includeSection){
            for(File f:list){
                if(f.getName().contains(botName + ".cs")|| botRealName.containsKey(f.getName()) ){
                    File file = new File(R20Dir + "APP\\Majestic.Bot.Job\\Config\\" + f.getParentFile().getName() + ".xml");
                    if(file.exists()){
                        list.add(file);
                    }
                    break;
                }
            }
        }

        //add the nlog file
        if(includeNlog){
            for(File f:list){
                if(f.getName().contains(botName + ".cs")|| botRealName.containsKey(f.getName()) ){
                    File file = new File(R20Dir + "APP\\Majestic.Bot.Job\\Config\\" + f.getParentFile().getName() + ".nlog");
                    if(file.exists()){
                        list.add(file);
                    }
                    break;
                }
            }
        }
        //remove the cs file
        if(!includeBot){
            for(File f:list){
                if(f.getName().contains(botName + ".cs")|| botRealName.containsKey(f.getName()) ){
                    list.remove(f);
                    break;
                }
            }
        }
        
        if(list.isEmpty()){
            return null;
        }
        else{
            return list;
        }
    }

    @Override
    public String exportFiles(String botName,
                              boolean includeBot,
                              boolean includeSection,
                              boolean includeNlog,
                              boolean includeUtil,
                              Date startDate){

        Collection<File> list = getExportableFiles(botName,includeBot,includeSection,
                                               includeNlog,includeUtil,startDate);

        // Collection<File> listWithSameNmaes = new Collection<File>();

        String target = "D:\\today\\" + sdf.format(CommonUtils.getToday()) + "\\" + botName + "\\";
        
        CommonUtils.copyFilesToDirectory(list,target);

        return CommonUtils.generatePathFile(list,target);
    }

}
