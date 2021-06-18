package org.jeecg.modules.devops.utils;

import org.jeecg.modules.devops.entity.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                                        String compileVerityFtpUserName) {
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

    public static String getActionOta(String id,String before_target_file, String before_ftp_username,
                                      String before_ftp_passwd, String after_target_file,
                                      String after_ftp_username,String after_ftp_passwd,String sv_platform_terrace) {
        return Config.JENKINS_BASE_URL
                + "job/otadiff/buildWithParameters?"
                + "id="+id
                + "&before_target_file=" + before_target_file.trim()
                + "&before_ftp_username=" + before_ftp_username
                + "&before_ftp_passwd=" + StringUtil.replaceString(before_ftp_passwd)
                + "&after_target_file=" + after_target_file.trim()
                + "&after_ftp_username=" + after_ftp_username
                + "&after_ftp_passwd=" + StringUtil.replaceString(after_ftp_passwd)
                + "&sv_platform_terrace=" + sv_platform_terrace;
    }
}
