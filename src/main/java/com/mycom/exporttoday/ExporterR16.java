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


public class ExporterR16 implements Exporter {
    public static final String R16Dir = "C:\\Repository\\qag\\Bot\\Releases\\R16\\";

    private  IOFileFilter excludedDirFilter = new NotFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties","HOST"}));
    
    @Override
    public Collection<String> getBotNamesChanged(Date startDate){
        if (startDate == null)
            startDate = CommonUtils.getToday();

        Set<String> botNameList = new HashSet<String>();

        Collection<File> files = FileUtils
            .listFiles(new File(R16Dir),
                       new AndFileFilter(new SuffixFileFilter(new String[]{".cs",".regex"},IOCase.INSENSITIVE),
                                         new AgeFileFilter(startDate,false)),
                       excludedDirFilter);

        for(File file:files){
            botNameList.add(file.getParentFile().getName());
        }

        return botNameList;
    }

    @Override
    public Collection<File> getExportableFiles(String botName,
                                               boolean includeBot,
                                               boolean includeSection,
                                               boolean includeNlog,
                                               boolean includeUtil,
                                               Date startDate){
        File targetDir = new File(R16Dir,botName);
        
        Collection<File> list = FileUtils
            .listFiles(targetDir,
                       new AndFileFilter(new SuffixFileFilter(new String[]{".cs",".regex"},IOCase.INSENSITIVE),
                                         new AgeFileFilter(startDate,false)),
                       excludedDirFilter);
        return list;
        
    }

    @Override
    public String exportFiles(Collection<File> list, String botName){

        String target = "D:\\today\\" + CommonUtils.sdf.format(CommonUtils.getToday()) + "\\" + botName + "\\";
        
        CommonUtils.copyFilesToDirectory(list,target);

        return CommonUtils.generatePathFile(list,target);
    
    }
}
