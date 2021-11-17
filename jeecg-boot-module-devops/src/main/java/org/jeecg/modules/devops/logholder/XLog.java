package org.jeecg.modules.devops.logholder;

public class XLog {
    private static final String TAG = "XLog";

    public static void debug(String str){
        System.out.println(TAG+"    debug     "+str);
    }

    public static void error(String str ,Object e){
        System.out.println(TAG + "     error     "+str +"        "+e.toString());
    }
}
