package com.mycom.filefilter;

import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import java.io.*;

public class ParentWildcardFileFilter extends AbstractFileFilter{

    private String parentnamewildcard;
    private WildcardFileFilter filter = null;
    IOCase caseSensitive = IOCase.INSENSITIVE;
    
    public ParentWildcardFileFilter(String wildcard){
        parentnamewildcard = wildcard;
    }

    public ParentWildcardFileFilter(String wildcard, IOCase sensitive){
        caseSensitive = sensitive;
    }
    
    @Override
    public boolean accept(File dir, String name){
        if(filter == null)
        {
            filter = new WildcardFileFilter(parentnamewildcard,caseSensitive);
        }
        return  filter.accept(dir);
    }

}
