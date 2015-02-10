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
    
    public void setUp(){}
    

}
