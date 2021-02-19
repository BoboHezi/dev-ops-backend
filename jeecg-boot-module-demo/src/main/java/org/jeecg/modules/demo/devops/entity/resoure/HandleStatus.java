package org.jeecg.modules.demo.devops.entity.resoure;

public class HandleStatus {
    public final static int STATUS_INIT= 0;
    public final static int STATUS_PROCESS_BEFORE_VERIFY= 1;
    public final static int STATUS_WAIT_BO_BE_VERIFIED= 2;
    public final static int STATUS_VERIFYING= 3;
    public final static int STATUS_VERIFIED_SUCCESS= 4;
    public final static int STATUS_VERIFIED_FAIL= 5;
    public final static int STATUS_ADDED_TO_QUEUE= 6;
    public final static int STATUS_JENKINS_TRIGGERED= 7;
    public final static int STATUS_CANCELD_BY_USER= 8;
    public final static int STATUS_SYSTEM_ERROR= 9;
    public final static int STATUS_WAIT_TO_ADD_QUEUE= 10;
    public final static int STATUS_ADD_TO_QUEUE_TIMEOUT= 11;
    public final static int STATUS_PUBLISHING= 12;
    public final static int STATUS_WAIT_TO_ADD_PUBLISH_QUEUE= 13;
    public final static int STATUS_ADDED_TO_PUBLISH_QUEUE= 14;
    public final static int STATUS_ADD_TO_PUBLISH_QUEUE_TIMEOUT= 15;
    public final static int STATUS_PUBLISH_JENKINS_TRIGGERED= 16;
    public final static int STATUS_PUBLISH_SYSTEM_ERROR= 17;
    public final static int STATUS_PUBLISH_OK= 18;
    public final static int STATUS_PUBLISH_CANCELD= 19;
    public final static int STATUS_RESET_TO_PUBLISH= 21;

    public final static int STATUS_OTA_START= 10;


    public static class Signs{
        public final static int         STATUS_INIT = 0;
        public final static int         STATUS_HANDLING_SIGN= 1;
        public final static int         STATUS_ADDED_TO_SIGN_QUEUE= 2;
        public final static int         STATUS_SIGN_SUCCESS= 3;
        public final static int         STATUS_SIGN_FAIL= 4;
        public final static int         STATUS_ADDED_TO_QUEUE= 6;
        public final static int         STATUS_JENKINS_TRIGGERED= 7;
        public final static int         STATUS_CANCELD_BY_USER= 8;
        public final static int         STATUS_SYSTEM_ERROR= 9;
        public final static int         STATUS_WAIT_TO_ADD_QUEUE= 10;
        public final static int         STATUS_ADD_TO_QUEUE_TIMEOUT= 11;
        public final static int         STATUS_SIGNING= 22;
    }

}
