package com.mycom.exporttoday;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.mycom.exporttoday.*;
import java.text.*;
import org.apache.commons.io.*;
import com.mycom.util.*;



public class ExporterR16Test extends TestCase {
    public ExporterR16Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ExporterR16Test.class );
    }

    Exporter exporter = new ExporterR16();
    
    public void testGetBotNamesChangedTodayCS(){

        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R16\\JobAutoNation\\JobAutoNation.cs");
        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());

        System.out.println("============JobAutoNation CS===========");
        System.out.println(StringUtils.join(botNames,"\n"));

        assertTrue(botNames.contains("JobAutoNation"));

    }

    public void testGetBotNamesChangedTodayRegex(){

        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R16\\JobANF\\JobANF.InvalidOrder.Regex");
        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());

        System.out.println("============JobANF CS===========");
        System.out.println(StringUtils.join(botNames,"\n"));

        assertTrue(botNames.contains("JobANF"));

    }

    

}
