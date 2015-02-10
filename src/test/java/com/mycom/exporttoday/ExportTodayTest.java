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
// import com.mycom.filter.*;

/**
 * Unit test for simple App.
 */
public class ExportTodayTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ExportTodayTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ExportTodayTest.class );
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

    public void testGetBotNamesChangedToday(){
        List<String> botNames = ExportToday.getBotNamesChangedToday();
        System.out.println("=======================");
        System.out.println(StringUtils.join(botNames,"\n"));
        System.out.println("=======================");
        assertTrue( true );
    }
    private static Collection<File> cashedFiles = null;
    
    public void testGetExportableFilesOfToday(){
        // Collection<File> files = ExportToday.getExportableFilesOfToday("Kohls",true,false,false,false);

        // System.out.println("=========include bot==============");
        // System.out.println(StringUtils.join(files,"\n"));
        // System.out.println("=======================");

        // cashedFiles = files;

        assertTrue( true );

    }

    public void testCopyFilesToDirectoryWithSameName(){
                
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // String target = "D:\\temp\\" + sdf.format(new Date()) + "\\Expedia\\";

        // ExportToday.copyFilesToDirectory(cashedFiles, target);

        // System.out.println(output);
 
        assertTrue(true);
    }
    
    public void setUp(){
        list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job_Browser\\TravelAgencies\\ExpediaUS.cs"));
        list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job_Browser\\TravelAgencies\\ExpediaUK.cs"));
        list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Entity\\TravelAgencies\\Expedia\\ExpediaUS.cs"));
     
    }
    
    private Collection<File> list = new LinkedList<File>();

    public void testFileHasSameName(){
        assertTrue(ExportToday.filesHasSameName(list));
    }

    public void testGroupFileBySameName(){

        System.out.println(StringUtils.join(list,"\n"));

        File[][] files = ExportToday.groupFileBySameName(list);
        System.out.print("files[0]:");
        System.out.println(StringUtils.join(files[0],"\n"));
        System.out.print("files[1]:");
        System.out.println(StringUtils.join(files[1],"\n"));

        assertNotNull(files);
            
    }

    public void testCopyFilesToDirectory(){

            
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String target = "D:\\temp\\" + sdf.format(new Date()) + "\\Expedia\\";

        ExportToday.copyFilesToDirectory(list, target);

        assertTrue(true);

    }

    public void testGeneratePathFile(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String target = "D:\\temp\\" + sdf.format(new Date()) + "\\Expedia\\";

        System.out.println(ExportToday.generatePathFile(list,target));
        assertTrue(true);

    }

    


}
