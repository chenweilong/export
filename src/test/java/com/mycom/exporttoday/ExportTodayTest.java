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
        // Collection<File> files = ExportToday.getExportableFilesOfToday("FacebookTopApps",true,false,false,false);

        // System.out.println("=========include bot==============");
        // System.out.println(StringUtils.join(files,"\n"));
        // System.out.println("=======================");

        // files = ExportToday.getExportableFilesOfToday("FacebookTopApps",false,false,false,false);
        
        // System.out.println("=========exclude bot==============");
        // System.out.println(StringUtils.join(files,"\n"));
        // System.out.println("=======================");

        // cashedFiles = files;

        assertTrue( true );

    }

    public void testCopyFilesToDirectory(){
        // System.out.println(cashedFiles);

        // assertNotNull(cashedFiles);

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // String target = "D:\\temp\\" + sdf.format(new Date()) + "\\FacebookTopApps\\";

        // String output = ExportToday.copyFilesToDirectory(cashedFiles, target);

        // System.out.println(output);

        assertTrue(true);
    }
    


}
