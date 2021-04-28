package org.jeecg.modules.devops.compile.service.impl;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import org.jeecg.modules.devops.compile.mapper.DevopsCompileMapper;
import org.jeecg.modules.devops.compile.service.IDevopsCompileService;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.sign.entity.DevopsSign;
import org.jeecg.modules.devops.utils.CurlUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsCompileServiceImpl extends ServiceImpl<DevopsCompileMapper, DevopsCompile> implements IDevopsCompileService , Job {
    @Autowired
    private DevopsCompileMapper devopsCompileMapper;

    /**
     * SERVER_STATUS 服务器 状态 0 空闲, 1 占用, 2 关机, 3 编译前占有该数据库
     */
    private final static String SERVER_STATUS = "0";
    private final static String SERVER_STATUS_3 = "3";

    /**
     * CODE_STATUS 代码同步 状态 -1 同步失败, 0 刚建立, 1 正在同步, 2 已经同步完成
     */
    private final static String CODE_STATUS = "2";

    /**
     * COMPILE_STATUS 编译代码 状态 -1 等待中, 0 成功, 1 初始化, 2 连接中 3 参数错误
     * 4 新项目名错误, 5 编译中, 6 编译失败, 7 编译停止 , 8 关联jenkins
     */
    private final static String COMPILE_STATUS__1 = "-1";
    private final static String COMPILE_STATUS_3 = "3";
    private final static String COMPILE_STATUS_2 = "2";

    private static LinkedList<DevopsCompile> queueCompile = new LinkedList<>();

    @Override
    public List<DevopsServer> getServerIP(String serverStatus, String platform, String codeStatus) {
        return devopsCompileMapper.queryServerStatus(serverStatus, platform, codeStatus);
    }

    @Override
    public DevopsFtp getFtp(String id) {
        return devopsCompileMapper.queryFtp(id);
    }

    @Override
    public DevopsSign getSign(String id) {
        return devopsCompileMapper.querySign(id);
    }


    @Override
    public DevopsCode getCodeDir(String serverId, String platform) {
        return devopsCompileMapper.queryCodeDir(serverId, platform);
    }

    /*查看log*/
    @Override
    public Messages<?> checkLog(DevopsCompile devopsCompile) {
        Messages messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
//        if (devopsCompile.getCompileLogUrl() != null) {
//            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
//            return messages;
//        }
        return messages;
    }

    @Override
    public void setServerStatus(String serverStatus, String serverID) {
        devopsCompileMapper.updateServerStatus(serverStatus, serverID);
    }

    @Override
    public void setCompileStatus(String compileStatus, String compileID) {
        devopsCompileMapper.updateCompileStatus(compileStatus,compileID);
    }
    @Override
    public Messages<?> autoCompile(DevopsCompile devopsCompile) {
        DevopsServer devopsServer = null;
        DevopsCode devopsCode = null;
        Messages<?> messages = messages = new Messages<>(Resoure.Code.UNFOUND_CODE, Resoure.Message.get(Resoure.Code.UNFOUND_CODE), null);
        if (devopsCompile.getId() == null) {
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }
        List<DevopsServer> devopsServersList = getServerIP(SERVER_STATUS, devopsCompile.getCompilePlatformId(), CODE_STATUS);
        System.out.println("服务器List —————————" + devopsServersList);

        String compileProjectName = devopsCompile.getCompileProjectId();
        if (compileProjectName == null) {
            compileProjectName = devopsCompile.getNewCompileProject();
        }
        if(compileProjectName == null){
            setCompileStatus(COMPILE_STATUS_3,devopsCompile.getId());
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }
        /*
         * 当没有可以用的服务器退出
         * */
        if (devopsServersList.size() == 0) {
            setCompileStatus(COMPILE_STATUS__1,devopsCompile.getId());
            queueCompile.add(devopsCompile);
            System.out.println(queueCompile);
            return messages;
        }
        DevopsFtp devopsFtp = getFtp(devopsCompile.getCompileSignFtpId());
        DevopsSign devopsSign = getSign(devopsCompile.getCompileLoginAccount());
        setCompileStatus(COMPILE_STATUS_2,devopsCompile.getId());
        devopsServer = devopsServersList.get(0);
        devopsCode = getCodeDir(devopsServer.getId(), devopsCompile.getCompilePlatformId());
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = Config.JENKINS_BASE_URL
                + "job/build-line/buildWithParameters?"
                + "project_name=" + compileProjectName
                + "&code_dir=" + devopsCode.getCodeDir()
                + "&server_hostname=" + devopsServer.getServerHost()
                + "&server_ip_address=" + devopsServer.getServerIp()
                + "&server_passwd=" + devopsServer.getServerPassword().replace("#", "%23")
                + "&build_variant=" + devopsCompile.getCompileVariant()
                + "&build_sign=" + (devopsCompile.getCompileIsSign().equals("Y") ? "true" : "false")
                + "&build_action=" + devopsCompile.getCompileAction()
                + "&build_verity=" + (devopsCompile.getCompileIsVerify().equals("Y") ? "true" : "false")
                + "&devops_host_id=" + devopsServer.getId()
                + "&devops_compile_id=" + devopsCompile.getId()
                + "&sv_platform_cclist=" + devopsCompile.getCompileSendEmail()
                + "&sign_ftp_upload_username=" + devopsFtp.getFtpUserName()
                + "&sign_ftp_upload_password=" + devopsFtp.getFtpPassword()
                + "&sv_platform_username=" +  devopsSign.getSignAccount()
                + "&sv_platform_password=" +  devopsSign.getSignPassword()
                + "&sv_platform_terrace=" + devopsCompile.getCompileSvPlatformTerrace()
                + "&publish_username=" + devopsCompile.getCompileVerityFtpUserName()
                + "&is_new_project=" + "false";
        System.out.println(curldata + curlurl);
        setServerStatus(SERVER_STATUS_3, devopsServer.getId());
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
        } else {
            setServerStatus(SERVER_STATUS, devopsServer.getId());
            messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
        }
        return messages;
    }

    @Override
    public void autoCompileQueue() {
        DevopsServer devopsServer = null;
        DevopsCode devopsCode = null;
        DevopsCompile autoDevopsCompile = null;
        System.out.println("定时任务");
        for (DevopsCompile devopsCompile : queueCompile) {
            List<DevopsServer> devopsServersList = getServerIP(SERVER_STATUS, devopsCompile.getCompilePlatformId(), "2");
            if (devopsServersList.size() != 0) {
                queueCompile.remove(devopsCompile);
                autoDevopsCompile = devopsCompile;
                devopsServer = devopsServersList.get(0);
                devopsCode = getCodeDir(devopsServer.getId(), autoDevopsCompile.getCompilePlatformId());
                String compileProjectName = autoDevopsCompile.getCompileProjectId();
                if (compileProjectName == null) {
                    compileProjectName = autoDevopsCompile.getNewCompileProject();
                }
                if(compileProjectName == null){
                    setCompileStatus(COMPILE_STATUS_3,devopsCompile.getId());
                    return ;
                }
                DevopsFtp devopsFtp = getFtp(devopsCompile.getCompileSignFtpId());
                DevopsSign devopsSign = getSign(devopsCompile.getCompileLoginAccount());
                setCompileStatus(COMPILE_STATUS_2,devopsCompile.getId());
                String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
                String curlurl = Config.JENKINS_BASE_URL
                        + "job/build-line/buildWithParameters?"
                        + "project_name=" + compileProjectName
                        + "&code_dir=" + devopsCode.getCodeDir()
                        + "&server_hostname=" + devopsServer.getServerHost()
                        + "&server_ip_address=" + devopsServer.getServerIp()
                        + "&server_passwd=" + devopsServer.getServerPassword().replace("#", "%23")
                        + "&build_variant=" + autoDevopsCompile.getCompileVariant()
                        + "&build_sign=" + (autoDevopsCompile.getCompileIsSign().equals("Y") ? "true" : "false")
                        + "&build_action=" + autoDevopsCompile.getCompileAction()
                        + "&build_verity=" + (autoDevopsCompile.getCompileIsVerify().equals("Y") ? "true" : "false")
                        + "&devops_host_id=" + devopsServer.getId()
                        + "&devops_compile_id=" + autoDevopsCompile.getId()
                        + "&sv_platform_cclist=" + devopsCompile.getCompileSendEmail()
                        + "&sign_ftp_upload_username=" + devopsFtp.getFtpUserName()
                        + "&sign_ftp_upload_password=" + devopsFtp.getFtpPassword()
                        + "&sv_platform_username=" +  devopsSign.getSignAccount()
                        + "&sv_platform_password=" +  devopsSign.getSignPassword()
                        + "&sv_platform_terrace=" + devopsCompile.getCompileSvPlatformTerrace()
                        + "&publish_username=" + devopsCompile.getCompileVerityFtpUserName()
                        + "&is_new_project=" + "false"
                        ;
                System.out.println(curldata + curlurl);
                setServerStatus(SERVER_STATUS_3, devopsServer.getId());
                String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
                if (!CurlUtil.run(cmds)) {
                    setServerStatus(SERVER_STATUS, devopsServer.getId());
                }
            }
        }
    }

    @Override
    public Messages<?> stopCompile(DevopsCompile devopsCompile) {
        Messages<?> messages = null;
        if (devopsCompile.getId() == null) {
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }

        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = Config.JENKINS_BASE_URL
                + "job/stop-build/buildWithParameters?"
                + "job_name_extra=" + devopsCompile.getCompileJenkinsJobName()
                + "&job_id=" + devopsCompile.getCompileJenkinsJobId();
        System.out.println(curldata + "         " + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
        } else {
            messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
        }
        return messages;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            autoCompileQueue();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
