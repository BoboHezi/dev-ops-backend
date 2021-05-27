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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsCompileServiceImpl extends ServiceImpl<DevopsCompileMapper, DevopsCompile> implements IDevopsCompileService, Job {
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
    private final static String COMPILE_STATUS_8 = "8";

    private Random random = new Random();

    @Override
    public List<DevopsServer> getServerIP(String serverStatus, String platform, String serverIp, String codeStatus) {
        if (serverIp == null || serverIp.isEmpty()) {
            return devopsCompileMapper.queryServerStatus(serverStatus, platform, codeStatus);
        }
        return devopsCompileMapper.queryServerStatusAndIp(serverStatus, platform, serverIp, codeStatus);
    }

    //检验服务器，是否有代码存在
    @Override
    public List<DevopsServer> getServerCode(String platform, String serverIp, String codeStatus) {
        if (serverIp == null || serverIp.isEmpty()) {
            return devopsCompileMapper.queryServerCode(platform, codeStatus);
        }
        return devopsCompileMapper.queryServerCodeAndIp(platform, serverIp, codeStatus);
    }

    @Override
    public DevopsFtp getFtp(String id) {
        return devopsCompileMapper.queryFtp(id);
    }

    @Override
    public DevopsSign getSign(String signAccount) {
        return devopsCompileMapper.querySign(signAccount);
    }


    @Override
    public DevopsCode getCodeDir(String serverId, String platform) {
        return devopsCompileMapper.queryCodeDir(serverId, platform);
    }

    @Override
    public List<DevopsCompile> getCompileQueue(String compileStatus) {
        return devopsCompileMapper.queryCompileQueue(compileStatus);
    }

    @Override
    public void setCompileQueueLevel(String id, String Level) {
        devopsCompileMapper.updateCompileQueueLevel(id, Level);
    }

    @Override
    public void cancelCompile(String id, String compileStatus) {
        devopsCompileMapper.updateCompileStatus(compileStatus, id);
    }

    @Override
    public DevopsCompile selectCompile(String id) {
        return devopsCompileMapper.queryCompile(id);
    }

    @Override
    public Messages<?> handleCopy(String id) {
        Messages messages = new Messages<>(Resoure.Code.FAIL, Resoure.Message.get(Resoure.Code.FAIL), null);
        System.out.println(id);
        DevopsCompile devopsCompile = selectCompile(id);
        System.out.println(devopsCompile.toString());
        if (devopsCompile != null) {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            devopsCompileMapper.insertHandleCopy((new Date()).getTime() + "" + random.nextInt(10000), devopsCompile.getCreateBy(), df.format(new Date()), devopsCompile.getSysOrgCode(), devopsCompile.getCompileName(),
                    devopsCompile.getCompileDesc(), devopsCompile.getCompilePlatformId(), devopsCompile.getNewCompileProject(),
                    devopsCompile.getCompileProjectId(), devopsCompile.getCompileServerIp(), devopsCompile.getCompileVariant(),
                    devopsCompile.getCompileAction(), devopsCompile.getCompileIsSign(), devopsCompile.getCompileIsVerify(),
                    devopsCompile.getCompileSendEmail(), devopsCompile.getCompileSignFtpId(), devopsCompile.getCompileLoginAccount(),
                    devopsCompile.getCompileVerityFtpUserName(), devopsCompile.getCompileSvPlatformTerrace(),
                    devopsCompile.getCherryPick());
            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
        }
        return messages;
    }

    /*查看log*/
    @Override
    public Messages<?> checkLog(DevopsCompile devopsCompile) {
        Messages messages = new Messages<>(Resoure.Code.FAIL, Resoure.Message.get(Resoure.Code.FAIL), null);
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
        devopsCompileMapper.updateCompileStatus(compileStatus, compileID);
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
        List<DevopsServer> devopsServersList = getServerIP(SERVER_STATUS, devopsCompile.getCompilePlatformId()
                , devopsCompile.getCompileServerIp(), CODE_STATUS);
        System.out.println("服务器List —————————" + devopsServersList);

        String compileProjectName = devopsCompile.getCompileProjectId();
        if (compileProjectName == null) {
            compileProjectName = devopsCompile.getNewCompileProject();
        }
        if (compileProjectName == null) {
            setCompileStatus(COMPILE_STATUS_3, devopsCompile.getId());
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }
        /*
         * 当没有可以用的服务器退出
         * */
        if (devopsServersList.size() == 0) {
            List<DevopsServer> devopsServersCodeList = getServerCode(devopsCompile.getCompilePlatformId()
                    , devopsCompile.getCompileServerIp(), CODE_STATUS);
            if (devopsServersCodeList.size() == 0) {
                setCompileStatus(COMPILE_STATUS_8, devopsCompile.getId());
            } else {
                setCompileStatus(COMPILE_STATUS__1, devopsCompile.getId());
            }
            return messages;
        }
        DevopsFtp devopsFtp = getFtp(devopsCompile.getCompileSignFtpId());
        DevopsSign devopsSign = getSign(devopsCompile.getCreateBy());
        setCompileStatus(COMPILE_STATUS_2, devopsCompile.getId());
        devopsServer = devopsServersList.get(0);
        devopsCode = getCodeDir(devopsServer.getId(), devopsCompile.getCompilePlatformId());
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = CurlUtil.getAutoCompile(compileProjectName, devopsCode.getCodeDir(), devopsServer.getServerHost(),
                devopsServer.getServerIp(), devopsServer.getServerPassword().replace("#", "%23"), devopsCompile.getCompileVariant()
                , (devopsCompile.getCompileIsSign().equals("Y") ? "true" : "false"), devopsCompile.getCompileAction(),
                (devopsCompile.getCompileIsVerify().equals("Y") ? "true" : "false"), devopsServer.getId(), devopsCompile.getId(),
                devopsCompile.getCompileSendEmail(), devopsCompile.getCompileSvPlatformTerrace(), devopsCompile.getCompileVerityFtpUserName());
        if (devopsCompile.getCherryPick() != null) {
            curlurl += CurlUtil.getAutoCompileAddCherryPick(devopsCompile.getCherryPick());
        }
        if (devopsFtp != null) {
            curlurl += CurlUtil.getAutoCompileAddFtp(devopsFtp.getFtpUserName(), devopsFtp.getFtpPassword());
        }
        if (devopsSign != null) {
            curlurl += CurlUtil.getAutoCompileAddSign(devopsSign.getSignAccount(), devopsSign.getSignPassword());
        }
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
        System.out.println("定时任务");
        List<DevopsCompile> devopsCompiles = getCompileQueue(COMPILE_STATUS__1);
        System.out.println(devopsCompiles.toString());
        for (DevopsCompile devopsCompile : devopsCompiles) {
            List<DevopsServer> devopsServersList = getServerIP(SERVER_STATUS, devopsCompile.getCompilePlatformId(), devopsCompile.getCompileServerIp(), CODE_STATUS);
            if (devopsServersList.size() != 0) {
                devopsServer = devopsServersList.get(0);
                devopsCode = getCodeDir(devopsServer.getId(), devopsCompile.getCompilePlatformId());
                String compileProjectName = devopsCompile.getCompileProjectId();
                if (compileProjectName == null) {
                    compileProjectName = devopsCompile.getNewCompileProject();
                }
                DevopsFtp devopsFtp = getFtp(devopsCompile.getCompileSignFtpId());
                DevopsSign devopsSign = getSign(devopsCompile.getCreateBy());
                setCompileStatus(COMPILE_STATUS_2, devopsCompile.getId());
                String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
                String curlurl = CurlUtil.getAutoCompile(compileProjectName, devopsCode.getCodeDir(), devopsServer.getServerHost(),
                        devopsServer.getServerIp(), devopsServer.getServerPassword().replace("#", "%23"), devopsCompile.getCompileVariant()
                        , (devopsCompile.getCompileIsSign().equals("Y") ? "true" : "false"), devopsCompile.getCompileAction(),
                        (devopsCompile.getCompileIsVerify().equals("Y") ? "true" : "false"), devopsServer.getId(), devopsCompile.getId(),
                        devopsCompile.getCompileSendEmail(), devopsCompile.getCompileSvPlatformTerrace(), devopsCompile.getCompileVerityFtpUserName());
                if (devopsCompile.getCherryPick() != null) {
                    curlurl += CurlUtil.getAutoCompileAddCherryPick(devopsCompile.getCherryPick());
                }
                if (devopsFtp != null) {
                    curlurl += CurlUtil.getAutoCompileAddFtp(devopsFtp.getFtpUserName(), devopsFtp.getFtpPassword());
                }
                if (devopsSign != null) {
                    curlurl += CurlUtil.getAutoCompileAddSign(devopsSign.getSignAccount(), devopsSign.getSignPassword());
                }
                System.out.println(curldata + curlurl);
                setServerStatus(SERVER_STATUS_3, devopsServer.getId());
                String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
                if (!CurlUtil.run(cmds)) {
                    setServerStatus(SERVER_STATUS, devopsServer.getId());
                }
            } else {
                setCompileQueueLevel(devopsCompile.getId(), (devopsCompile.getCompileQueueLevel() + 1) + "");
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
        String curlurl = CurlUtil.getStopCompile(devopsCompile.getCompileJenkinsJobName(), devopsCompile.getCompileJenkinsJobId());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
