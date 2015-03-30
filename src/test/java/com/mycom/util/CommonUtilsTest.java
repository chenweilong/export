package com.mycom.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// import org.apache.commons.io.*;
// import org.apache.commons.io.filefilter.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.mycom.util.*;
import com.mycom.exporttoday.*;
import java.text.*;


/**
 * Unit test for simple App.
 */
public class CommonUtilsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CommonUtilsTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CommonUtilsTest.class );
    }


    private static Collection<File> cashedFiles = null;
    
    public void setUp(){
        list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job_Browser\\TravelAgencies\\ExpediaUS.cs"));
        list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Bot.Job_Browser\\TravelAgencies\\ExpediaUK.cs"));
        list.add(new File("C:\\Repository\\qag\\Bot\\Releases\\R20\\APP\\Majestic.Entity\\TravelAgencies\\Expedia\\ExpediaUS.cs"));
     
    }
    
    private Collection<File> list = new LinkedList<File>();

    public void testFileHasSameName(){
        assertTrue(CommonUtils.filesHasSameName(list));
    }

    public void testGroupFileBySameName(){

        File[][] files = CommonUtils.groupFileBySameName(list);

        assertNotNull(files);
        assertEquals(files.length, 2);
            
    }

    public void testCopyFilesToDirectory(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String target = "D:\\temp\\" + sdf.format(new Date()) + "\\Expedia\\";

        CommonUtils.copyFilesToDirectory(list, target);

        File file = new File(target + "Expedia\\ExpediaUS.cs");
        
        assertTrue(file.exists());
        
        file = new File(target + "TravelAgencies\\ExpediaUS.cs");
        
        assertTrue(file.exists());

    }

    public void testGeneratePathFile(){
    	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String target = "D:\\temp\\" + sdf.format(new Date()) + "\\Expedia\\";

    	CommonUtils.generatePathFile(list, target);
    	
        File file = new File(target,"path.txt");

        assertTrue(file.exists());

    }

}
