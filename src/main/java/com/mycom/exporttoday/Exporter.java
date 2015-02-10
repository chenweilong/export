package com.mycom.exporttoday;

import java.util.*;

public interface Exporter{

    List<String> getBotNamesChangedToday();
    String exportFiles(String botname,boolean includeBot, boolean includeSection,
                       boolean includeNlog,boolean includeUtil,Date startDate);
}
