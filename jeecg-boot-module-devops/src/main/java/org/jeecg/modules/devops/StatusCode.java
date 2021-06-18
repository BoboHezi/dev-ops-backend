package org.jeecg.modules.devops;

public class StatusCode {
    /**
     * SERVER_STATUS 服务器 状态 0 空闲, 1 占用, 2 关机, 3 编译前占有该数据库, 4 报废, 5 重启服务器中 , 6 未知
     */
    public final static String SERVER_STATUS_0 = "0";
    public final static String SERVER_STATUS_2 = "2";
    public final static String SERVER_STATUS_3 = "3";
    public final static String SERVER_STATUS_4 = "4";
    public final static String SERVER_STATUS_5 = "5";

    /**
     * CODE_STATUS 代码同步 状态 -1 同步失败, 0 刚建立, 1 正在同步, 2 已经同步完成
     */
    public final static String CODE_STATUS = "2";

    /**
     * COMPILE_STATUS 编译代码 状态 -1 等待中, 0 成功, 1 初始化, 2 连接中 3 参数错误
     * 4 新项目名错误, 5 编译中, 6 编译失败, 7 编译停止 , 8 未找到代码 ,9 cherry_pick 失败
     * 10 上传服务器失败 , 11 上传验收失败 12 预处理 , 13 环境错误
     */
    public final static String COMPILE_STATUS__1 = "-1";
    public final static String COMPILE_STATUS_0 = "0";
    public final static String COMPILE_STATUS_1 = "1";
    public final static String COMPILE_STATUS_2 = "2";
    public final static String COMPILE_STATUS_3 = "3";
    public final static String COMPILE_STATUS_4 = "4";
    public final static String COMPILE_STATUS_5 = "5";
    public final static String COMPILE_STATUS_6 = "6";
    public final static String COMPILE_STATUS_7 = "7";
    public final static String COMPILE_STATUS_8 = "8";
    public final static String COMPILE_STATUS_9 = "9";
    public final static String COMPILE_STATUS_10 = "10";
    public final static String COMPILE_STATUS_11 = "11";
    public final static String COMPILE_STATUS_12 = "12";
    public final static String COMPILE_STATUS_13 = "13";

    /**
     * OTA_STATUS 编译代码 状态 -1 等待中, 0 成功, 1 初始化, 2 编译中 3 失败
     */
    public final static String OTA_STATUS__1 = "-1";
    public final static String OTA_STATUS_0 = "0";
    public final static String OTA_STATUS_1 = "1";
    public final static String OTA_STATUS_2 = "2";
    public final static String OTA_STATUS_3 = "3";

}
