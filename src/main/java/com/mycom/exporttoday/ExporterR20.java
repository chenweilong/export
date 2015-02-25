package com.mycom.exporttoday;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import com.mycom.util.*;
import com.mycom.filefilter.*;
import org.apache.commons.lang3.*;

public class ExporterR20 implements Exporter {
    public static final String R20Dir = "C:\\Repository\\qag\\Bot\\Releases\\R20\\";
    
    public static final boolean DEBUG = true;
    
    public static Properties botAliasName;
    private  Collection<File> allBotFileList;
    private  IOFileFilter excludedDirFilter = new NotFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties"}));

    //load the properties file
    static {
        try{
            botAliasName = new Properties();
            botAliasName.load(ExporterR20.class.getResourceAsStream("/aliasname.properties"));
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
    public Collection<String> getBotNamesChanged(Date startDate){
        if (startDate == null)
            startDate = CommonUtils.getToday();

        Set<String> botNameList = new HashSet<String>();

        List<File> regexList = null;
        Collection<File> files = getFilesChangedInDir("APP\\Majestic.Bot.Job",
                                                      new SuffixFileFilter(new String[]{".cs",".regex"},IOCase.INSENSITIVE),
                                                      excludedDirFilter,
                                                      startDate);
        files.addAll(getFilesChangedInDir("APP\\Majestic.Bot.Job_Browser",new SuffixFileFilter(".cs"),excludedDirFilter,startDate));

        
        for(File file:files){
            if(file.getName().toLowerCase().contains(".cs") ){
                botNameList.add(getBotAliasName(file));
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

        //add the regex file's corresponding bot file name to the hashset.
        for(File regexFile : regexList){
            for(File botfile:allBotFileList){
                String name = getBotAliasName(botfile);
                if(regexFile.getName().toLowerCase().contains(name.toLowerCase())){
                    botNameList.add(name);
                    break;
                }
            }
        }

        return botNameList;
    }

    private String getBotAliasName(File file){
        String name = file.getName();
        if(botAliasName.containsKey(name)){
            return botAliasName.getProperty(name);
        } else{
            return name.substring(0,name.length() -3 );
        }
    }

    public File getBotFileByName(String name){
        if(allBotFileList == null)
            allBotFileList = getAllBotFiles();
        String realname = getBotRealName(name);

        for(File file: allBotFileList){
            if(file.getName().equals(realname)){
                return file;
            }
        }

        return null;
    }

    public String getBotRealName(String name){
        Set<Map.Entry<Object,Object>> entries = botAliasName.entrySet();
        for(Map.Entry<Object,Object> entry : entries){
            if(entry.getValue().equals(name)){
                return (String)entry.getKey();
            }
        }
        return name + ".cs";
    }

    private Collection<File> getFilesChangedInDir(String targetdir, IOFileFilter filter,IOFileFilter dirfilter, Date date){
                
        //check file modified today

        Collection<File> list = FileUtils.listFiles("".equals(targetdir)?new File(R20Dir):new File(R20Dir,targetdir),
                                                    FileFilterUtils.and(filter,new AgeFileFilter(date,false)),
                                                    dirfilter);
        return list;
    }
    
    @Override
    public Collection<File> getExportableFiles(String botName, 
        boolean includeSection,
        boolean includeNlog,
        boolean includeUtil,
        Date startDate){
        
        File file = getBotFileByName(botName);

        //System.out.println(file);
        
        Collection<File> list = null;

        if (file == null){
            list = getFilesChangedInDir("",
                new AndFileFilter(new PathNameRegexFileFilter(botName) ,
                    new NotFileFilter(new SuffixFileFilter(".csproj",IOCase.INSENSITIVE))),
                excludedDirFilter,
                startDate);
        } else {
            String dirname = file.getParentFile().getName();

            //System.out.println(dirname);

            String fileName = file.getName();
            
            //main bot file
            list = getFilesChangedInDir("APP\\Majestic.Bot.Job\\" + dirname,
                                        new AndFileFilter(new WildcardFileFilter("*" + fileName + "*",IOCase.INSENSITIVE),
                                                          new NotFileFilter(new SuffixFileFilter(".csproj",IOCase.INSENSITIVE))),
                                        FalseFileFilter.INSTANCE,
                                        startDate);

            // System.out.println("debug:" + StringUtils.join(list,"\n"));

            //regex file
            list.addAll(getFilesChangedInDir("APP\\Majestic.Bot.Job\\RegexFiles",
                                             new AndFileFilter(new WildcardFileFilter("*" + botName + "*",IOCase.INSENSITIVE) ,
                                                               new SuffixFileFilter(".regex",IOCase.INSENSITIVE)),
                                             FalseFileFilter.INSTANCE,
                                             startDate));


            //dao file
            list.addAll(getFilesChangedInDir("APP\\Majestic.Dal\\" + dirname,
                                             new AndFileFilter(new WildcardFileFilter("*" + fileName + "*",IOCase.INSENSITIVE),
                                                               new NotFileFilter(new SuffixFileFilter(".csproj",IOCase.INSENSITIVE))),
                                             FalseFileFilter.INSTANCE,
                                             startDate));
            //entity file
            list.addAll(getFilesChangedInDir("APP\\Majestic.Entity\\" + dirname,
                                             new AndFileFilter(new PathNameRegexFileFilter(fileName) ,
                                                               new NotFileFilter(new SuffixFileFilter(".csproj",IOCase.INSENSITIVE))),
                                             excludedDirFilter,
                                             startDate));
        }

        if (includeUtil) {
            list.addAll(getFilesChangedInDir("Framework\\Util",new SuffixFileFilter(".cs",IOCase.INSENSITIVE),excludedDirFilter,startDate));
        }
        
        //add the section file
        if(includeSection){
            File xmlfile = new File(R20Dir + "APP\\Majestic.Bot.Job\\Config\\" + file.getParentFile().getName() + ".xml");
            if(xmlfile.exists()){
                list.add(xmlfile);
            }
        }

        //add the nlog file
        if(includeNlog){
            File nlogfile = new File(R20Dir + "APP\\Majestic.Bot.Job\\Config\\" + file.getParentFile().getName() + ".nlog");
            if(nlogfile.exists()){
                list.add(nlogfile);
            }
        }
        // //remove the cs file
        // if(!includeBot){
        //     for(File f:list){
        //         if(f.getName().contains(botName + ".cs")|| botAliasName.containsKey(f.getName()) ){
        //             list.remove(f);
        //             break;
        //         }
        //     }
        // }
        
        if(list.isEmpty()){
            return null;
        }
        else{
            return list;
        }
    }

    @Override
    public String exportFiles(Collection<File> list, String botName){

        // Collection<File> listWithSameNmaes = new Collection<File>();
        if(list.size() == 0){
            return null;
        }
        
        String target = "D:\\today\\" + CommonUtils.sdf.format(CommonUtils.getToday()) + "\\" + botName + "\\";
        
        CommonUtils.copyFilesToDirectory(list,target);

        return CommonUtils.generatePathFile(list,target);
    }

}
