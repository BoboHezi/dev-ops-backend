package org.jeecg.modules.devops.compile.service.impl;

import com.google.common.eventbus.EventBus;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.devops.cache.entity.DevopsCompileCache;
import org.jeecg.modules.devops.cache.mapper.DevopsCompileCacheMapper;
import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.EventBusTools;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import org.jeecg.modules.devops.compile.mapper.DevopsCompileMapper;
import org.jeecg.modules.devops.compile.service.IDevopsCompileService;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.EmailMessage;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.messages.MessagesEnum;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.mapper.DevopsServerMapper;
import org.jeecg.modules.devops.sign.entity.DevopsSign;
import org.jeecg.modules.devops.utils.CurlUtil;
import org.jeecg.modules.devops.utils.StringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.swing.event.ChangeEvent;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.jeecg.modules.devops.StatusCode.*;

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

    @Autowired
    private DevopsServerMapper devopsServerMapper;

    @Autowired
    private DevopsCompileCacheMapper devopsCompileCacheMapper;

    private Random random = new Random();

    @Override
    public List<DevopsServer> getServerIP(String serverStatus, String platform, String serverIp, String codeStatus) {
        if (serverIp == null || serverIp.isEmpty()) {
            return devopsServerMapper.queryServerStatus(serverStatus, platform, codeStatus);
        }
        return devopsServerMapper.queryServerStatusAndIp(serverStatus, platform, serverIp, codeStatus);
    }

    //检验服务器，是否有代码存在
    @Override
    public List<DevopsServer> getServerCode(String platform, String serverIp, String codeStatus) {
        if (serverIp == null || serverIp.isEmpty()) {
            return devopsServerMapper.queryServerCode(platform, codeStatus);
        }
        return devopsServerMapper.queryServerCodeAndIp(platform, serverIp, codeStatus);
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

    /**
     * 清楚jenkins数据
     *
     * @param id
     */
    @Override
    public void clearJenkins(String id) {
        devopsCompileMapper.clearJenkinsData(id);
    }

    @Override
    public Messages<?> requestServer(String id) {
        Messages messages = new Messages<>(Resoure.Code.FAIL, Resoure.Message.get(Resoure.Code.FAIL), null);
        DevopsCompile devopsCompile = getCompile(id);
        DevopsServer devopsServer = getServerLinkIP(devopsCompile.getCompileServerIp());
        if (devopsServer.getServerLinkStatus() == 1) {
            devopsServerMapper.updateRequestServer(id, devopsCompile.getCompileServerIp());
            messages.setMsg("操作成功");
        } else {
            messages.setMsg("请求失败,请确认是否空闲");
        }
        return messages;
    }

    @Override
    public DevopsServer getServerLinkIP(String serverIp) {
        return devopsServerMapper.queryRequestServer(serverIp);
    }

    @Override
    public DevopsCompile getCompile(String id) {
        return devopsCompileMapper.queryCompile(id);
    }

    @Override
    public Messages<?> handleCopy(String id) {
        Messages messages = new Messages<>(Resoure.Code.FAIL, Resoure.Message.get(Resoure.Code.FAIL), null);
        DevopsCompile devopsCompile = getCompile(id);
        System.out.println(devopsCompile.toString());
        if (devopsCompile != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            devopsCompileMapper.insertHandleCopy((new Date()).getTime() + "" + random.nextInt(10000), devopsCompile.getCreateBy(), df.format(new Date()), devopsCompile.getSysOrgCode(), devopsCompile.getCompileName(),
                    devopsCompile.getCompileDesc(), devopsCompile.getCompilePlatformId(), devopsCompile.getNewCompileProject(),
                    devopsCompile.getCompileProjectId(), "", devopsCompile.getCompileVariant(),
                    devopsCompile.getCompileAction(), devopsCompile.getCompileIsSign(), devopsCompile.getCompileIsVerify(),
                    devopsCompile.getCompileSendEmail(), devopsCompile.getCompileSignFtpId(), devopsCompile.getCompileLoginAccount(),
                    devopsCompile.getCompileVerityFtpUserName(), devopsCompile.getCompileSvPlatformTerrace(),
                    devopsCompile.getCherryPick());
            messages = Messages.Success();
        }
        return messages;
    }

    @Override
    public void setServerStatus(String serverStatus, String serverID) {
        devopsServerMapper.updateServerStatus(serverStatus, serverID);
    }

    private EventBus eventBus = null;

    @Override
    public void setCompileStatus(String compileStatus, String compileID) {
        System.out.println("setCompileStatus compileStatus   " + compileStatus + "    compileID    " + compileID);
        if (compileStatus == null || compileID == null) {
            return;
        }
        devopsCompileMapper.updateCompileStatus(compileStatus, compileID);
        DevopsCompile devopsCompile = getCompile(compileID);
        String messages_title = null;
        String messages_content = null;
        switch (compileStatus) {
            case COMPILE_STATUS_0:
                DevopsCompileCache devopsCompileCache = null;
                try{
                    devopsCompileCache = devopsCompileCacheMapper.queryCompileCache(compileID);
                }catch (Exception e){
                    e.printStackTrace();
                }
                messages_title = MessagesEnum.COMPILE_SUCCESS.getMsg();
                DevopsFtp devopsFtp = getFtp(devopsCompile.getCompileSignFtpId());
                messages_content = String.format(MessagesEnum.COMPILE_SUCCESS_CONTENT,
                        devopsCompile.getCompileBuildId(), devopsCompile.getCompileSignFtpUrl(),
                        devopsFtp.getFtpUserName(), devopsCompile.getCompileSignId(),
                        devopsCompile.getCompileVerityId(), devopsCompile.getNewCompileProject(),
                        devopsCompile.getCompileSvPlatformTerrace(), devopsCompileCache == null ? "空" : devopsCompileCache.getCacheLocation());
                break;
            case COMPILE_STATUS_3:
                messages_title = MessagesEnum.COMPILE_CHECK_FAIL.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_CHECK_FAIL.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;
            case COMPILE_STATUS_4:
                messages_title = MessagesEnum.COMPILE_PROJECT_NOT_FOUND.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_PROJECT_NOT_FOUND.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;
            case COMPILE_STATUS_6:
                messages_title = MessagesEnum.COMPILE_BUILD_FAILED.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_BUILD_FAILED.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;
            case COMPILE_STATUS_9:
                messages_title = MessagesEnum.COMPILE_CP_FAILED.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_CP_FAILED.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;
            case COMPILE_STATUS_10:
                messages_title = MessagesEnum.COMPILE_UPLOAD_FAILED.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_UPLOAD_FAILED.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;
            case COMPILE_STATUS_11:
                messages_title = MessagesEnum.COMPILE_SV_FAILED.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_SV_FAILED.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;
            case COMPILE_STATUS_13:
                messages_title = MessagesEnum.COMPILE_PREPARE_FAILED.getMsg();
                messages_content = String.format(MessagesEnum.COMPILE_FAIL_CONTENT,
                        devopsCompile.getCompileBuildId(), MessagesEnum.COMPILE_PREPARE_FAILED.getMsg_detail(),
                        devopsCompile.getNewCompileProject(), devopsCompile.getCompileSvPlatformTerrace());
                break;

            default:
                break;
        }
        if (messages_title != null) {

            String es_title = String.format(MessagesEnum.COMPILE.getMsg(), devopsCompile.getNewCompileProject(),
                    devopsCompile.getCompileBuildId()) + messages_title;
            String[] es_receiver = (devopsCompile.getCompileSendEmail()).split(",");
            String es_content = String.format(MessagesEnum.MESSAGE_HEADER.getMsg(), devopsCompile.getCreateBy())
                    + messages_content
                    + MessagesEnum.MESSAGE_END.getMsg();
            EmailMessage emailMessage = new EmailMessage(es_receiver, es_title, es_content);
            if (eventBus == null) {
                eventBus = new EventBus();
                eventBus.register(new EventBusTools());
            }
            ChangeEvent event = new ChangeEvent(emailMessage);
            eventBus.post(event);
            //*/服务器重启
            List<DevopsServer> devopsServersCodeList = getServerCode(devopsCompile.getCompilePlatformId()
                    , devopsCompile.getCompileServerIp(), CODE_STATUS);
            if (devopsServersCodeList.size() == 1) {
                DevopsServer devopsServer = devopsServersCodeList.get(0);
                if (devopsServer.getServerPerformance() - 10 < devopsServer.getServerPerformanceLowValue()) {
                    restartServer(devopsServer);
                } else {
                    devopsServerMapper.updateServerPerformance(devopsServer.getId(), devopsServer.getServerPerformance() - 10);
                }
            }
            //*/
            //  autoCompileQueue();
        }
    }

    public void restartServer(DevopsServer devopsServer) {

//        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
//        String curlurl = CurlUtil.getRestartServer(devopsServer.getId(), devopsServer.getServerIp(), devopsServer.getServerHost(), devopsServer.getServerPassword());
//        System.out.println(curlurl);
//        devopsServerMapper.updateServerStatus(SERVER_STATUS_5, devopsServer.getId());
//        devopsServerMapper.updateServerPerformance(devopsServer.getId(),devopsServer.getServerPerformanceInitialValue());
//        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
//        if (CurlUtil.run(cmds)) {
//            return "成功";
//        } else {
//            devopsServerMapper.updateServerStatus(SERVER_STATUS_0, devopsServer.getId());
//            return "失败";
//        }
    }

    static boolean FLAG_CDY = true;

    @Override
    public Messages<?> autoCompile(String compileID) {
        DevopsCompile devopsCompile = getCompile(compileID);
        Messages<?> messages = messages = new Messages<>(Resoure.Code.UNFOUND_CODE, Resoure.Message.get(Resoure.Code.UNFOUND_CODE), null);
        List<DevopsServer> devopsServersCodeList = getServerCode(devopsCompile.getCompilePlatformId()
                , devopsCompile.getCompileServerIp(), CODE_STATUS);
        if (devopsServersCodeList.size() == 0) {
            setCompileStatus(COMPILE_STATUS_8, devopsCompile.getId());
        } else {
            setCompileStatus(COMPILE_STATUS__1, devopsCompile.getId());
            FLAG_CDY = false;
            autoCompileQueue();
        }
        return messages;
    }

    @Override
    public void autoCompileQueue() {
        List<DevopsCompile> devopsCompiles = getCompileQueue(COMPILE_STATUS__1);
        System.out.println(devopsCompiles.toString());
        for (DevopsCompile devopsCompile : devopsCompiles) {
            List<DevopsServer> devopsServersList = getServerIP(SERVER_STATUS_0, devopsCompile.getCompilePlatformId(), devopsCompile.getCompileServerIp(), CODE_STATUS);
            if (devopsServersList.size() != 0) {
                setServerStatus(SERVER_STATUS_3, devopsServersList.get(0).getId());
                Compile(devopsServersList.get(0), devopsCompile);
            } else {
                setCompileQueueLevel(devopsCompile.getId(), (devopsCompile.getCompileQueueLevel() + 1) + "");
            }
        }
    }

    private void Compile(DevopsServer devopsServer, DevopsCompile devopsCompile) {

        clearJenkins(devopsCompile.getId());

        DevopsCode devopsCode = getCodeDir(devopsServer.getId(), devopsCompile.getCompilePlatformId());
        String compileProjectName = devopsCompile.getNewCompileProject();
        DevopsFtp devopsFtp = getFtp(devopsCompile.getCompileSignFtpId());
        DevopsSign devopsSign = getSign(devopsCompile.getCreateBy());
        setCompileStatus(COMPILE_STATUS_2, devopsCompile.getId());
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        if (StringUtil.isEmpty(compileProjectName)) {
            setCompileStatus(COMPILE_STATUS_3, devopsCompile.getId());
            System.out.println("compileProjectName" + compileProjectName);
            setServerStatus(SERVER_STATUS_0, devopsServer.getId());
            return;
        }
        if (StringUtil.isEmpty(devopsCompile.getCompileSendEmail())) {
            System.out.println("getCompileSendEmail" + devopsCompile.getCompileSendEmail());
            setCompileStatus(COMPILE_STATUS_3, devopsCompile.getId());
            setServerStatus(SERVER_STATUS_0, devopsServer.getId());
            return;
        }
        String curlurl = CurlUtil.getAutoCompile(compileProjectName, devopsCode.getCodeDir(), devopsServer.getServerHost(),
                devopsServer.getServerIp(), devopsServer.getServerPassword(), devopsCompile.getCompileVariant()
                , (devopsCompile.getCompileIsSign().equals("Y") ? "true" : "false"), devopsCompile.getCompileAction().equals("ota_factory") ? "ota" : devopsCompile.getCompileAction(),
                (devopsCompile.getCompileIsVerify().equals("Y") ? "true" : "false"), devopsServer.getId(), devopsCompile.getId(),
                devopsCompile.getCompileSendEmail(), devopsCompile.getCompileSvPlatformTerrace(), devopsCompile.getCompileVerityFtpUserName(), devopsCompile.getCompileQueueLevel());

        if (devopsCompile.getCherryPick() != null) {
            curlurl += CurlUtil.getAutoCompileAddCherryPick(devopsCompile.getCherryPick());
        }
        if (devopsFtp != null) {
            curlurl += CurlUtil.getAutoCompileAddFtp(devopsFtp.getFtpUserName(), devopsFtp.getFtpPassword());
        }
        if (devopsSign != null) {
            curlurl += CurlUtil.getAutoCompileAddSign(devopsSign.getSignAccount(), devopsSign.getSignPassword());
            curlurl += CurlUtil.getAutoCompileAction(devopsCompile.getCompileAction());
        }
        System.out.println(curldata + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (!CurlUtil.run(cmds)) {
            setServerStatus(SERVER_STATUS_0, devopsServer.getId());
        }
    }

    @Override
    public Result<?> stopCompile(DevopsCompile devopsCompile) {
        if (devopsCompile.getId() == null) {
            return Result.error("ID 不存在!");
        }
        setCompileStatus(COMPILE_STATUS_14, devopsCompile.getId());
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = CurlUtil.getStopCompile(devopsCompile.getCompileJenkinsJobName(), devopsCompile.getCompileJenkinsJobId());
        System.out.println(curldata + "         " + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
            return Result.OK("操作成功!");
        } else {
            return Result.OK("操作失败");
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (FLAG_CDY) {
                autoCompileQueue();
            } else {
                FLAG_CDY = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
