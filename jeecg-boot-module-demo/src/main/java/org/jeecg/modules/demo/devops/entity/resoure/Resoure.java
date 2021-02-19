package org.jeecg.modules.demo.devops.entity.resoure;

import java.util.HashMap;
import java.util.Map;

public class Resoure {
    public static class Code {
        public final static int SUCCESS = 1000;
        public final static int FAIL = 1001;


        public final static int USERPASSWORDERROR = 1002;
        public final static int PASSWORDERROR = 1003;
        public final static int USEREXIST = 1004;
        public final static int AUTHENTICATIONFAIL = 1005;
        public final static int LOGINTIMEOUT = 1006;
        public final static int USERNOTEXIST = 1007;
        public final static int GETUSERFAIL = 1008;
        public final static int TOKENNOTEXIST = 1009;
        public final static int USER_PASSWORD_LENGTH_ERROR = 10010;
        public final static int PASSWORD_SAME = 10011;

        public final static int TASK_SEARCH_CONNECT_ISNULL = 10012;
        public final static int INPUT_ISNULL = 10013;
        public final static int INPUT_EXIST = 10014;
        public final static int ID_NOT_EXIST = 10015;
        public final static int CURL_RUN_FAIL = 10016;
        public final static int DATA_TOKEN_INVALID = 10017;
        public final static int SYSTEM_ERROR = 10018;
        public final static int DATA_LOSER_RASON = 10019;
    }

    public static class Message {
        private static final Map<Integer, String> map;

        static {
            map = new HashMap<Integer, String>();
            map.put(Code.FAIL, "FAIL");
            map.put(Code.SUCCESS, "SUCCESS");
            map.put(Code.USERPASSWORDERROR, "用户名或密码错误");
            map.put(Code.PASSWORDERROR, "密码错误");
            map.put(Code.USEREXIST, "用户已存在");
            map.put(Code.AUTHENTICATIONFAIL, "用户token验证失败");
            map.put(Code.LOGINTIMEOUT, "登陆超时");
            map.put(Code.USERNOTEXIST, "用户不存在");
            map.put(Code.GETUSERFAIL, "从token获取user失败");
            map.put(Code.TOKENNOTEXIST, "token不从在");
            map.put(Code.USER_PASSWORD_LENGTH_ERROR, "用户名或密码长度错误");
            map.put(Code.PASSWORD_SAME, "两次密码相同");
            map.put(Code.TASK_SEARCH_CONNECT_ISNULL, "搜索关键字为空");
            map.put(Code.INPUT_ISNULL, "输入不能为空");
            map.put(Code.INPUT_EXIST, "输入已存在");
            map.put(Code.ID_NOT_EXIST, "ID不存在");
            map.put(Code.CURL_RUN_FAIL, "CURL 执行失败");
            map.put(Code.DATA_TOKEN_INVALID, "token invalid");
            map.put(Code.SYSTEM_ERROR, "系统异常，请稍后重试");
            map.put(Code.DATA_LOSER_RASON, "验收不通过原因不能为空");

        }

        public static String get(int code) {
            return map.get(code);
        }
    }

    public static class ConstStr {
        public final static String SENDER = "Droi Service<droi_service@droi.com>";
        public final static String MAIL_TITLE = "%s验收申请%s已提交";
        public final static String MAIL_SUCCESS_TITLE = "%s验收任务%s验收通过";
        public final static String MAIL_FAIL_TITLE = "%s验收任务%s验收不通过";
        public final static String MAIL_CANCEL_TITLE = "%s验收任务%s已取消";

    }
}
