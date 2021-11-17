package org.jeecg.modules.devops.utils;

import org.jeecg.modules.devops.entity.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurlUtil {
    public static boolean run(String[] cmds) {

        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.redirectErrorStream(true);
        Process p;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            p = pb.start();
            BufferedReader br = null;
            String line = null;
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                System.out.println("\t" + line);
            }
            br.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getAutoCompile(String projectName, String dir, String host, String serverIp, String serverPwd,
                                        String compileVariant, String compileSign, String compileAction, String compileVerify,
                                        String serverId, String compileId, String compileEmail, String compileSvPlatformTerrace,
                                        String compileVerityFtpUserName, int level) {
        if (level < 50) {
            level = 0;
        } else if (level < 100) {
            level = 1;
        } else if (level < 150) {
            level = 2;
        } else {
            level = 3;
        }
        return Config.JENKINS_BASE_URL
                + "job/build-line/buildWithParameters?"
                + "project_name=" + projectName.trim()
                + "&code_dir=" + dir
                + "&server_hostname=" + host
                + "&server_ip_address=" + serverIp
                + "&server_passwd=" + StringUtil.replaceString(serverPwd)
                + "&build_variant=" + compileVariant
                + "&build_sign=" + compileSign
                + "&build_action=" + compileAction
                + "&build_verity=" + compileVerify
                + "&devops_host_id=" + serverId
                + "&devops_compile_id=" + compileId
                + "&sv_platform_cclist=" + compileEmail.trim()
                + "&sv_platform_terrace=" + compileSvPlatformTerrace
                + "&sv_verity_level=" + level
                + "&publish_username=" + compileVerityFtpUserName;
    }

    public static String getAutoCompileAddFtp(String userName, String password) {
        return "&sign_ftp_upload_username=" + userName
                + "&sign_ftp_upload_passwd=" + password;
    }

    public static String getAutoCompileAddSign(String account, String password) {
        return "&sv_platform_username=" + account
                + "&sv_platform_passwd=" + password;
    }

    public static String getStopCompile(String JenkinsJobName, String JenkinsJobId) {
        return Config.JENKINS_BASE_URL
                + "job/stop-build/buildWithParameters?"
                + "job_name_extra=" + JenkinsJobName
                + "&job_id=" + JenkinsJobId;
    }

    public static String getRestartServer(String id, String serverIp, String serverHost, String serverPwd) {
        return Config.JENKINS_BASE_URL
                + "job/reboot-host/buildWithParameters?"
                + "devops_host_id=" + id
                + "&server_ip_address=" + serverIp
                + "&server_hostname=" + serverHost
                + "&server_passwd=" + StringUtil.replaceString(serverPwd);
    }

    public static String getAutoCompileAction(String verityPurpose) {
        if (verityPurpose.equals("ota")) {
            return "&sv_verity_purpose=official";
        } else {
            return "&sv_verity_purpose=factory";
        }
    }

    public static String getAutoCompileAddCherryPick(String cherryPick) {
        return "&cherry_picks=" + StringUtil.replaceString(cherryPick.trim());
    }

    public static String getActionOta(String id, String before_target_file, String before_ftp_username,
                                      String before_ftp_passwd, String after_target_file,
                                      String after_ftp_username, String after_ftp_passwd, String sv_platform_terrace,
                                      String serverIp, String serverHost, String serverPassword) {
        return Config.JENKINS_BASE_URL
                + "job/otadiff/buildWithParameters?"
                + "id=" + id
                + "&before_target_file=" + before_target_file.trim()
                + "&before_ftp_username=" + before_ftp_username
                + "&before_ftp_passwd=" + StringUtil.replaceString(before_ftp_passwd)
                + "&after_target_file=" + after_target_file.trim()
                + "&after_ftp_username=" + after_ftp_username
                + "&after_ftp_passwd=" + StringUtil.replaceString(after_ftp_passwd)
                + "&sv_platform_terrace=" + sv_platform_terrace
                + "&ota_factory_host_ip=" + serverIp
                + "&ota_factory_host_user=" + serverHost
                + "&ota_factory_host_pwd=" + StringUtil.replaceString(serverPassword);
    }

    public static String getLinkServer(String serverIp, String serverHost, String serverPassword,
                                       String id, Boolean stop_terminal) {
        return Config.JENKINS_BASE_URL
                + "job/web-terminal/buildWithParameters?"
                + "server_ip_address=" + serverIp
                + "&server_hostname=" + serverHost
                + "&server_passwd=" + StringUtil.replaceString(serverPassword)
                + "&stop_terminal=" + stop_terminal
                + "&id=" + id;
    }

    public static String OTA_REGEX = "^ftp://([^@]*)@([^/|^:]*)(:[\\d]+)?/(.*/)?(.*\\.zip)";

    public static String getFtpName(String otaDir) {
        Pattern rPattern = Pattern.compile(OTA_REGEX);
        Matcher matcher = rPattern.matcher(otaDir);
        boolean result = matcher.find();
        if (result)
            return matcher.group(1);
        return "";
    }

    public static boolean getFtpVerityDir(String otaDir) {
        Pattern rPattern = Pattern.compile(OTA_REGEX);
        Matcher matcher = rPattern.matcher(otaDir);
        boolean result = matcher.find();
        System.out.println(matcher.group(2));
        if (result)
            if (matcher.group(2).equals("upload.droi.com") || matcher.group(2).equals("192.168.0.131"))
                return true;
        return false;
    }

}
