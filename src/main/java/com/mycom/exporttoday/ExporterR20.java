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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExporterR20 implements Exporter {
    public static final String R20Dir = "C:\\Repository\\qag\\Bot\\Releases\\R20\\";
    Logger logger = LoggerFactory.getLogger(ExporterR20.class);

    public static final boolean DEBUG = true;

    public static Properties botAliasName;
    private Collection<File> allBotFileList;
    private IOFileFilter excludedDirFilter = new NotFileFilter(
        new NameFileFilter(new String[] { "bin", "obj", "Properties" }));

    // load the properties file
    static {
        try {
            botAliasName = new Properties();
            botAliasName.load(ExporterR20.class
                .getResourceAsStream("/aliasname.properties"));
        } catch (IOException e) {
            System.out.println("-----------failue-------------");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Collection<File> getAllBotFiles() {

        return FileUtils.listFiles(new File(R20Dir, "APP\\Majestic.Bot.Job"),
            new SuffixFileFilter(".cs"), new NotFileFilter(
                new NameFileFilter(new String[] { "bin", "obj",
                                                  "Properties", "RegexFiles", "Config" })));

    }

    @Override
    public Collection<String> getBotNamesChanged(Date startDate) {
        if (startDate == null)
            startDate = CommonUtils.getToday();

        Set<String> botNameList = new HashSet<String>();

        List<File> regexList = null;
        Collection<File> files = getFilesChangedInDir("APP\\Majestic.Bot.Job",
            new SuffixFileFilter(new String[] { ".cs", ".regex" },
                IOCase.INSENSITIVE), excludedDirFilter, startDate);
        files.addAll(getFilesChangedInDir("APP\\Majestic.Bot.Job_Browser",
                new SuffixFileFilter(".cs"), excludedDirFilter, startDate));

        for (File file : files) {
            String fileName = file.getName();
            if (fileName.toLowerCase().contains(".cs")) {
                // botNameList.add(getBotAliasName(file));
                botNameList.add(fileName.substring(0, fileName.length() - 3));
            } else if (fileName.toLowerCase().contains(".regex")) {
                if (regexList == null) {
                    regexList = new ArrayList<File>();
                }
                regexList.add(file);
            }
        }

        if (regexList == null) {
            return botNameList;
        }

        if (allBotFileList == null)
            allBotFileList = getAllBotFiles();

        // add the regex file's corresponding bot file name to the hashset.
        for (File regexFile : regexList) {
            for (File botfile : allBotFileList) {
                String name = getBotAliasName(botfile);
                if (regexFile.getName().toLowerCase()
                    .contains(name.toLowerCase())) {
                    // botNameList.add(name);
                    String fileName = botfile.getName();
                    botNameList
                        .add(fileName.substring(0, fileName.length() - 3));
                    break;
                }
            }
        }

        return botNameList;
    }

    private String getBotAliasName(File file) {
        String name = file.getName();
        if (botAliasName.containsKey(name)) {
            return botAliasName.getProperty(name);
        } else {
            return name.substring(0, name.length() - 3);
        }
    }

    public File getBotFileByAliasName(String name) {
        if (allBotFileList == null)
            allBotFileList = getAllBotFiles();
        String realname = getBotRealName(name);

        for (File file : allBotFileList) {
            if (file.getName().equals(realname)) {
                return file;
            }
        }

        return null;
    }

    public File getBotFileByName(String name) {
        if (allBotFileList == null)
            allBotFileList = getAllBotFiles();

        for (File file : allBotFileList) {
            if (file.getName().equals(name + ".cs")) {
                return file;
            }
        }

        return null;
    }

    public String getBotRealName(String name) {
        Set<Map.Entry<Object, Object>> entries = botAliasName.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            if (entry.getValue().equals(name)) {
                return (String) entry.getKey();
            }
        }
        return name + ".cs";
    }

    private Collection<File> getFilesChangedInDir(String targetdir,
        IOFileFilter filter, IOFileFilter dirfilter, Date date) {

        // check file modified today

        File dir = null;

        if (targetdir.startsWith("c:")) {
            dir = new File(targetdir);
        } else if ("".equals(targetdir)) {
            dir = new File(R20Dir);
        } else {
            dir = new File(R20Dir, targetdir);
        }

        if (dir.exists()) {
            return FileUtils
                .listFiles(dir, FileFilterUtils.and(filter,
                        new AgeFileFilter(date, false)), dirfilter);
        }
        else{
			
            return new LinkedList<File>();
        }

    }

    @Override
    public Collection<File> getExportableFiles(String botName, Date startDate,
        boolean... flags) {

        logger.debug("start");

        boolean includeSection = false;
        boolean includeNlog = false;
        boolean includeUtil = false;
        boolean qascript = false;
        boolean includeAppConfig = false;
        boolean includeProjectFiles = false;

        try {
            if (flags.length > 0) {

                includeSection = flags[0];
                includeNlog = flags[1];
                includeUtil = flags[2];
                qascript = flags[3];
                includeAppConfig = flags[4];
                includeProjectFiles = flags[5];

            }
        } catch (Exception e) {

        }

        File file = getBotFileByName(botName);

        // System.out.println(file);

        Collection<File> list = null;

        String aliasBotName = getBotAliasName(file);

        if (file == null) {

            list = getFilesChangedInDir("", new AndFileFilter(
                    new PathNameRegexFileFilter(aliasBotName),
                    new NotFileFilter(new SuffixFileFilter(".csproj",
                            IOCase.INSENSITIVE))), excludedDirFilter, startDate);
        } else {
            String dirname = file.getParentFile().getName();

            logger.debug(dirname);

            list = new LinkedList<File>();
            if (file.lastModified() > startDate.getTime()) {
                list.add(file);
            }

            logger.debug(String.valueOf(list.size()));

            try {
                // regex file
                list.addAll(getFilesChangedInDir(
                        "APP\\Majestic.Bot.Job\\RegexFiles", new AndFileFilter(
                            new OrFileFilter(new WildcardFileFilter("*"
                                    + aliasBotName + "*",
                                    IOCase.INSENSITIVE),
                                new WildcardFileFilter("*" + botName
                                    + "*", IOCase.INSENSITIVE)),
                            new SuffixFileFilter(".regex",
                                IOCase.INSENSITIVE)),
                        FalseFileFilter.INSTANCE, startDate));

                // dao file
                list.addAll(getFilesChangedInDir("APP\\Majestic.Dal\\"
                        + dirname, new AndFileFilter(new WildcardFileFilter("*"
                                + botName + "*", IOCase.INSENSITIVE),
                            new NotFileFilter(new SuffixFileFilter(".csproj",
                                    IOCase.INSENSITIVE))),
                        FalseFileFilter.INSTANCE, startDate));
                // entity file
                list.addAll(getFilesChangedInDir("APP\\Majestic.Entity\\"
                        + dirname, new AndFileFilter(
                            new PathNameRegexFileFilter(aliasBotName),
                            new NotFileFilter(new SuffixFileFilter(".csproj",
                                    IOCase.INSENSITIVE))), excludedDirFilter,
                        startDate));

                // database file
                list.addAll(getFilesChangedInDir("Database\\",
                        new OrFileFilter(new PathNameRegexFileFilter(
                                aliasBotName), new PathNameRegexFileFilter(
                                    botName)), TrueFileFilter.INSTANCE, startDate));

                logger.debug(String.valueOf(list.size()));
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }

        }

        if (includeUtil) {
            list.addAll(getFilesChangedInDir("Framework\\Util",
                    new SuffixFileFilter(".cs", IOCase.INSENSITIVE),
                    excludedDirFilter, startDate));
        }

        // add the section file
        if (includeSection) {
            File xmlfile = new File(R20Dir + "APP\\Majestic.Bot.Job\\Config\\"
                + file.getParentFile().getName() + ".xml");
            if (xmlfile.exists()) {
                list.add(xmlfile);
            }
        }

        // add the nlog file
        if (includeNlog) {
            File nlogfile = new File(R20Dir + "APP\\Majestic.Bot.Job\\Config\\"
                + file.getParentFile().getName() + ".nlog");
            if (nlogfile.exists()) {
                list.add(nlogfile);
            }
        }

        // qa script file
        if (qascript) {

            Collection<File> qafiles = getFilesChangedInDir(
                "c:\\Repository\\qag\\Bot\\QA\\", new WildcardFileFilter(
                    "*" + botName + "*", IOCase.INSENSITIVE),
                TrueFileFilter.INSTANCE, startDate);
            logger.debug(qafiles.size() + "");
            list.addAll(qafiles);

        }
		
        if(includeAppConfig){
			
            list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Core\\Host\\app.config"));
			
        }
		
        if(includeProjectFiles){
            logger.debug("start process project file");
            Set<File> projectFiles = new HashSet<File>();
            for(File f: list){
                if(f.getName().endsWith(".cs")){
		
                    Collection<File> targetFiles = FileUtils.listFiles(f.getParentFile(),new String[]{"csproj"},false);
                    logger.debug(StringUtils.join(targetFiles,","));
                    if(targetFiles != null && targetFiles.size()>0) {
                        File projectFile = targetFiles.toArray(new File[targetFiles.size()])[0];
                        projectFiles.add(projectFile);
                    }
                    
                    File propertiesFile = new File(new File(f.getParent(),"Properties"), "AssemblyInfo.cs");
                    logger.debug(propertiesFile.getAbsolutePath());
                    if(propertiesFile.exists()){
                        projectFiles.add(propertiesFile);
                    } 
                }
            }
            list.addAll(projectFiles);
        }

        if (list.isEmpty()) {
            return null;
        } else {
            logger.debug("final");
            logger.debug(StringUtils.join(list,"\n"));
            return list;
        }
    }

    private boolean fileAlreadyInList(Collection<File> list, File projectFile) {
        for(File f: list){
            if(f.getName().equals(projectFile.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String exportFiles(Collection<File> list, String botName) {

        // Collection<File> listWithSameNmaes = new Collection<File>();
        if (list.size() == 0) {
            return null;
        }

        String target = "D:\\today\\"
            + CommonUtils.sdf.format(CommonUtils.getToday()) + "\\"
            + botName + "\\";

        CommonUtils.copyFilesToDirectory(list, target);

        return CommonUtils.generatePathFile(list, target);
    }

}
