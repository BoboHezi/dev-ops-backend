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
                + "project_name=" + projectName
                + "&code_dir=" + dir
                + "&server_hostname=" + host
                + "&server_ip_address=" + serverIp
                + "&server_passwd=" + serverPwd
                + "&build_variant=" + compileVariant
                + "&build_sign=" + compileSign
                + "&build_action=" + compileAction
                + "&build_verity=" + compileVerify
                + "&devops_host_id=" + serverId
                + "&devops_compile_id=" + compileId
                + "&sv_platform_cclist=" + compileEmail
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

    public static String getAutoCompileAddCherryPick(String cherryPick) {
        return "&cherry_picks=" + cherryPick.replace(" ", "%20").replace("\\", "%5C")
                .replace("\"", "%5C%22").replace(":", "%3A").replace("/", "%2F")
                .replace("&", "%26").replace(";", "%3B").replace("@", "%40");
    }

}
