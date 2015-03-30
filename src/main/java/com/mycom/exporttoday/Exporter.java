package com.mycom.exporttoday;

import java.util.*;
import java.io.*;

public interface Exporter{

    Collection<String> getBotNamesChanged(Date startDate);
    
    String exportFiles(Collection<File> list,String botname);

    Collection<File> getExportableFiles(String botname,Date startDate, boolean... flags);
}
