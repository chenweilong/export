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
        return new TestSuite( ExportTodayTest.class );
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

        CommonUtils.copyFilesToDirectory(list, target);

        assertTrue(true);

    }

    public void testGeneratePathFile(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String target = "D:\\temp\\" + sdf.format(new Date()) + "\\Expedia\\";

        System.out.println(CommonUtils.generatePathFile(list,target));

        File file = new File(target,"path.txt");

        assertTrue(file.exists());

    }

}
