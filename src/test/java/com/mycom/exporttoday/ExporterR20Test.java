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

        assertTrue(botNames.contains("TestBot"));

    }
    
    SimpleDateFormat sdf; 
    Date yesterday;
    
    {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			yesterday = sdf.parse("2015-03-10");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void testGetBotNamesChangedTodayFullRegex(){
        
    	
        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RetailListing\\TestBot.cs");
        try{
            file.setLastModified(yesterday.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Retaillisting_TestBot_ABCDEFG.regex");

        try{
        	 FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Retaillisting_TB_ABCDEFG.regex");

        try{
        	file.setLastModified(yesterday.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }

        
        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());
        
        assertTrue(botNames.contains("TestBot"));

    }
    
    public void testGetBotNamesChangedTodayAliasRegex(){
        
    	
        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RetailListing\\TestBot.cs");
        try{
            file.setLastModified(yesterday.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Retaillisting_TestBot_ABCDEFG.regex");

        try{
        	file.setLastModified(yesterday.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Retaillisting_TB_ABCDEFG.regex");

        try{
       	    FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Collection<String> botNames = exporter.getBotNamesChanged(CommonUtils.getToday());
        
        assertTrue(botNames.contains("TestBot"));

    }

    

    public void testGetExportableFiles(){
    	
    	touchAll();
    	
        Collection<File> files = exporter.getExportableFiles("TestBot",false,false,false,CommonUtils.getToday());
        
        assertEquals(files.size(),botFiles.size());
        
    }

    Exporter exporter = new ExporterR20();
    List<File> botFiles = new LinkedList<File>();
    
    public void touchAll(){
        File file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RetailListing\\TestBot.cs");
        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }

        botFiles.add(file);
                
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Retaillisting_TestBot_ABCDEFG.regex");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        botFiles.add(file);
        
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job\\RegexFiles\\Retaillisting_TB_ABCDEFG.regex");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }

        botFiles.add(file);
        
        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Dal\\RetailListing\\TestBotDao.cs");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        botFiles.add(file);

        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Entity\\RetailListing\\TestBot\\ABCD.cs");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        botFiles.add(file);
        

        file = new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\Database\\RetailListingNew\\TestBot\\update.sql");

        try{
            FileUtils.touch(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        botFiles.add(file);
        
    }
    

}
