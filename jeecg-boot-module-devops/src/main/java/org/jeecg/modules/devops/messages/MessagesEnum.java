package org.jeecg.modules.devops.messages;

public enum MessagesEnum {
    OTA_SUCCESS("已成功"),
    OTA_ERROR("失败"),
    OTA_TITLE("OTA 差分包任务 %s "),
    OTA_CONTENT("你的OTA编译已经成功<br> 目标地址：%s"),
    OTA_CONTENT_FAIL("你的OTA编译失败"),
    COMPILE("%1$s编译任务%2$s"),
    COMPILE_SUCCESS("已成功"),
    COMPILE_CHECK_FAIL("参数错误","参数填写有问题，请重新检查一遍，重新编译"),
    COMPILE_PROJECT_NOT_FOUND("新项目名错误","您填写的项目名在代码下未找到，请检查你的项目名或对应的项目是否正确"),
    COMPILE_BUILD_FAILED("编译失败","您的项目编译过程中出现了问题，请检查你的代码后重新编译"),
    COMPILE_CP_FAILED("cherry pick失败","请检查你cherry pick是否正确,注意先后顺序"),
    COMPILE_UPLOAD_FAILED("上传文件失败","文件上传失败了,请手动去上传你的文件,进行签名验收"),
    COMPILE_SV_FAILED("签名验收失败","提交签名验收失败,请重新进行签名验收"),
    COMPILE_PREPARE_FAILED("环境错误","你使用的服务器环境有问题，请更换服务器"),
    LINK_SERVER_SUCCESS("服务器创建连接已生效"),
    LINK_SERVER_FAILED("服务器创建连接失败","服务器创建连接失败,请联系管理员，重新重试一下"),
    LINK_SERVER_CONTEXT("服务器连接地址: %s<br>服务器连接时间为8小时,用完记得提醒管理员,保证资源利用"),
    MESSAGE_HEADER("HI %s:<br><br><br>"),
    MESSAGE_END("<br><br>有任何疑问,请咨询Droi.<br>多谢！<br>web网页<br>http://192.168.48.113"),
    MESSAGE_NOTES("<br>注意事项"),
    OTA_CHECK_FAIL("参数错误","参数填写有问题，请重新检查一遍，重新制作"),
    OTA_DOWNLOAD_FAIL("下载target包失败","下载target升级包失败，请检查对应的账号，ftp地址是否存在问题"),
    OTA_PLATFORM_FAIL("平台选择错误","选择平台失败，请选择正常的平台用于制作ota"),
    OTA_SYSTEM_FAIL("系统错误","请联系管理员，进行重启电脑，查询问题点"),
    OTA_UPLOAD_FAIL("升级包上传失败","请连接ftp管理者，ftp可能空间不足"),
    OTA_NONE("未知错误","该问题没有考虑到,可能是因为平台问题的500错误，请联系开发人员");


    public static String COMPILE_SUCCESS_CONTENT = "编译任务ID:  %1$s <br>ftp地址:  %2$s <br>ftp账号: %3$s " +
            "<br>签名ID:  %4$s <br>验收ID:  %5$s <br>项目:  %6$s <br>平台:  %7$s <br>编译相关文件存放路径:  %8$s";
    public static String COMPILE_FAIL_CONTENT =  "编译任务ID:  %1$s <br>失败原因:  %2$s <br>项目:  %3$s<br>平台:  %4$s";

    private String msg;

    private String msg_detail;

    MessagesEnum (String msg){
        this.msg = msg;
    }

    MessagesEnum (String msg ,String msg_detail){
        this.msg = msg;
        this.msg_detail = msg_detail;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsg_detail() {
        return msg_detail;
    }

    public void setMsg_detail(String msg_detail) {
        this.msg_detail = msg_detail;
    }
}
