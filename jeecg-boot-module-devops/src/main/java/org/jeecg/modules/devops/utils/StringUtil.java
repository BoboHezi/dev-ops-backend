package org.jeecg.modules.devops.utils;

public class StringUtil {
    public static String replaceString(String str) {
        return str.replace(" ", "%20").replace("\\", "%5C")
                .replace("\"", "%5C%22").replace(":", "%3A").replace("/", "%2F")
                .replace("&", "%26").replace(";", "%3B")
                .replace("@", "%40").replace("#", "%23").replace("*", "%2A");
    }

    public static boolean isEmpty(String str) {
        if(str == null){
            return true;
        }
        if (str.trim().equals(""))
            return true;
        return false;
    }
}
