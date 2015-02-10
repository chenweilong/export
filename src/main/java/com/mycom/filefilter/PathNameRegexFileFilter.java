package com.mycom.filefilter;


import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import java.io.*;
import java.util.regex.*;

public class PathNameRegexFileFilter extends AbstractFileFilter{

    private Pattern p;

    IOCase caseSensitive = IOCase.INSENSITIVE;
    
    public PathNameRegexFileFilter(String pattern){
        p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
    }

    public PathNameRegexFileFilter(String pattern, IOCase sensitive){
        this(pattern);
        caseSensitive = sensitive;
    }
    
    @Override
    public boolean accept(File file){
        Matcher m = p.matcher(file.getPath());
        return m.find();
    }

}
