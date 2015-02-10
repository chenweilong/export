package com.mycom.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.mycom.filefilter.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {


        // Collection<File> files = FileUtils.listFiles(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP"),
        //                                              FileFilterUtils.or(new WildcardFileFilter("*Amazon*.cs",IOCase.INSENSITIVE),
        //                                                                 new WildcardFileFilter("*Amazon*.regex",IOCase.INSENSITIVE),
        //                                                                 new ParentWildcardFileFilter("*amazon*")),
        //                                              FileFilterUtils.notFileFilter(new NameFileFilter(new String[]{"bin","obj","Properties"})));
        
        // String results = StringUtils.join(files,"\n");
        // System.out.println(results);
        assertTrue( true );

    }
}
