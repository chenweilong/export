package com.mycom.exporttoday;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// import org.apache.commons.io.*;
// import org.apache.commons.io.filefilter.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.mycom.exporttoday.*;
import java.text.*;
import org.apache.commons.io.*;
import com.mycom.util.*;

/**
 * Unit test for simple App.
 */
public class ExporterR20Test
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ExporterR20Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ExporterR20Test.class );
    }

    /**
     * Rigourous Test :-)
     */
    // public void testGetAllBotFiles()
    // {
    //     Collection<File> list = ExportToday.getAllBotFiles();
    //     System.out.println(StringUtils.join(list,"\n"));
    //     assertTrue( true );
    // }

    public void testGetBotNamesChangedTodayCS(){

        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RetailListing\\TestBot.cs");
        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());

        System.out.println("============TestBot CS===========");
        System.out.println(StringUtils.join(botNames,"\n"));

        assertTrue(botNames.contains("TestBot"));

    }

    public void testGetBotNamesChangedTodayRegex(){
        
        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Testttt_Kohls_Product2SizeOptions.Regex");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());

        System.out.println("==========Kohls Regex=============");
        System.out.println(StringUtils.join(botNames,"\n"));
        
        assertTrue(botNames.contains("Kohls"));

    }

    public void testGetBotNamesChangedTodayAliasNameCS(){
        
        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RetailListing\\AmazonHardlinesTest.cs");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());

        System.out.println("==========Amazon CS============");
        System.out.println(StringUtils.join(botNames,"\n"));

        assertTrue(botNames.contains("AmazonHardlinesT"));
    }

    public void testGetBotNamesChangedTodayAliasNameRegex(){
        
        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\TEST_ECommerce_OpenTableRC_GetLinksFromMenuItems.regex");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());

        System.out.println("==========OpenTable CS============");
        System.out.println(StringUtils.join(botNames,"\n"));

        assertTrue(botNames.contains("OpenTable"));
    }

    public void testGetBotFileByName(){
        System.out.println("==========testGetBotFileByName============");
        File f = ((ExporterR20)exporter).getBotFileByName("TestBot");
        System.out.println(f.getPath());
        assertNotNull(f);
    }

    public void testGetExportableFiles(){
        System.out.println("==========testGetExportableFiles============");
        Collection<File> files = exporter.getExportableFiles("TestBot",false,false,false,CommonUtils.getToday());
        assertTrue(files.size() == 1);
        System.out.println(StringUtils.join(files,"\n"));

        files = exporter.getExportableFiles("AmazonHardlinesT",false,false,false,CommonUtils.getToday());
        assertTrue(files.size() == 1);
        System.out.println(StringUtils.join(files,"\n"));
        
        assertTrue(true);
    }


    
    Exporter exporter = new ExporterR20();
    
    public void setUp(){}
    

}
