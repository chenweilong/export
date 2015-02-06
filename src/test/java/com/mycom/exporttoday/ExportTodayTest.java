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
    public void testApp()
    {
        Collection<File> list = ExportToday.getAllBotFiles();
        System.out.println(StringUtils.join(list,"\n"));
        assertTrue( true );
    }
}
