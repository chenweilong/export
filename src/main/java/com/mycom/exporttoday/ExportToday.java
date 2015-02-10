package com.mycom.exporttoday;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import com.mycom.filefilter.*;
import org.apache.commons.lang3.*;

public class ExportTodayR20 implements Exporter {
    public static final String R20Dir = "C:\\Repository\\qag\\Bot\\Releases\\R20\\";
    
    public static final boolean DEBUG = true;
    
    public static Properties botRealName;
    private static Collection<File> allBotFileList;
    private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    private static IOFileFilter excludedDirFilter = new NotFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties"}));

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
    
    public static Collection<File> getAllBotFiles(){
                
        return FileUtils.listFiles(new File(R20Dir, "APP\\Majestic.Bot.Job"),
                                   new SuffixFileFilter(".cs"),
                                   new NotFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties","RegexFiles","Config"})));
        //return FileUtil.walk(new File(Export.botDir + "APP\\Majestic.Bot.Job"),".cs",".csproj");
    }
        
    private static Collection<File> getFilesChangedTodayInDir(String targetdir, IOFileFilter filter){
        return getFilesChangedAfterDateInDir(targetdir,filter,getToday());
    }
            
    private static Collection<File> getFilesChangedAfterDateInDir(String targetdir, IOFileFilter filter, Date date){
                
        //check file modified today
        Collection<File> list = FileUtils.listFiles(new File(R20Dir,targetdir),
                                                    FileFilterUtils.and(filter,new AgeFileFilter(date,false)),
                                                    excludedDirFilter);
        return list;
    }

    public static Date getToday(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        return today;
    }
        
    public static List<String> getBotNamesChangedToday(){
        List<String> botNameList = new LinkedList<String>();

        List<File> regexList = null;
        Collection<File> files = getFilesChangedTodayInDir("APP\\Majestic.Bot.Job",new SuffixFileFilter(new String[]{".cs",".regex"}));
        files.addAll(getFilesChangedTodayInDir("APP\\Majestic.Bot.Job_Browser",new SuffixFileFilter(".cs")));
                
        for(File file:files){
            if(botRealName.containsKey(file.getName())){
                botNameList.add((String)botRealName.get(file.getName()));
            } else if(file.getName().toLowerCase().contains(".cs") ){
                botNameList.add(file.getName().replace(".cs",""));
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
            for(File bot:allBotFileList){
                if(regexFile.getName().toLowerCase().contains(getBotName(bot.getName().toLowerCase()))){
                    botnameset.add(bot.getName().replace(".cs",""));
                    break;
                }
            }
        }
        botNameList.addAll(botnameset);

        return botNameList;
    }
        
    private static String getBotName(String fileName) {
        if(botRealName.containsKey(fileName)){
            return (String)botRealName.get(fileName);
        } else{
            return fileName.replace(".cs","");
        }
    }

    public static Collection<File> getExportableFilesOfToday(String botName,boolean includeBot,boolean includeSection,boolean includeNlog,boolean includeUtil){

        return getExportableFiles(botName, includeBot, includeSection, includeNlog, includeUtil,getToday());
    }
     
    private static Collection<File> getExportableFiles(String botName,boolean includeBot,boolean includeSection,
                                               boolean includeNlog,boolean includeUtil,Date startDate){

        // IOFileFilter filter = FileFilterUtils.or(new AndFileFilter(new WildcardFileFilter("*" + botName + "*",IOCase.INSENSITIVE),
        //                                                            new SuffixFileFilter(".cs",IOCase.INSENSITIVE)),
        //                                          new AndFileFilter(new WildcardFileFilter("*" + botName + "*",IOCase.INSENSITIVE),
        //                                                            new SuffixFileFilter(".regex",IOCase.INSENSITIVE)),
        //                                          new ParentWildcardFileFilter("*amazon*"))
        // ;

        Collection<File> list = getFilesChangedAfterDateInDir("",
                                                              new AndFileFilter(new PathNameRegexFileFilter(botName) ,
                                                                                new NotFileFilter(new SuffixFileFilter(".csproj",IOCase.INSENSITIVE))),
                                                              startDate);

        if (includeUtil) {
            list.addAll(getFilesChangedAfterDateInDir("Framework\\Util",new SuffixFileFilter(".cs",IOCase.INSENSITIVE),startDate));
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

    public static String exportFiles(String botName,boolean includeBot,boolean includeSection,
                                               boolean includeNlog,boolean includeUtil,Date startDate){

        Collection<File> list = getExportableFiles(botName,includeBot,includeSection,
                                               includeNlog,includeUtil,startDate);

        // Collection<File> listWithSameNmaes = new Collection<File>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String target = "D:\\today\\" + sdf.format(getToday()) + "\\" + botName + "\\";
        
        copyFilesToDirectory(list,target);

        return generatePathFile(list,target);
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

    public static File[][] groupFileBySameName(Collection<File> list){
        File[] files = list.toArray(new File[list.size()]);
        int[] group = new int[files.length];
        int max = 1;
        

        for(int i = 0; i<files.length-1;i++){
            if(group[i] != 0){
                continue;
            }
            for(int j=i+1; j<files.length;j++){

                // if(DEBUG){
                //     System.out.println(files[i].getName());
                //     System.out.println(files[j].getName());
                // }

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

    public static void main(String... arg){
        //exportTodaysFileByBot("BuyBuyBaby",true,true,false,false);
    
    }
}
