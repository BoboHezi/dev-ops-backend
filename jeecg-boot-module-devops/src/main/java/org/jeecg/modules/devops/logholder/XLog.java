package org.jeecg.modules.devops.logholder;

public class XLog {
    public static void debug(String str){
        System.out.println("debug     "+str);
    }

    public static void error(String str ,Object e){
        System.out.println("error     "+str +"        "+e.toString());
    }
}
