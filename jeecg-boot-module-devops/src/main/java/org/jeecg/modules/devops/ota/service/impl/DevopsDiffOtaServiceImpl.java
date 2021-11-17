package org.jeecg.modules.devops.ota.service.impl;

import com.google.common.eventbus.EventBus;
import org.jeecg.modules.devops.compile.EventBusTools;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.EmailMessage;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.messages.MessagesEnum;
import org.jeecg.modules.devops.ota.entity.DevopsDiffOta;
import org.jeecg.modules.devops.ota.mapper.DevopsDiffOtaMapper;
import org.jeecg.modules.devops.ota.service.IDevopsDiffOtaService;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.mapper.DevopsServerMapper;
import org.jeecg.modules.devops.upload.entity.DevopsUpload;
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
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.jeecg.modules.devops.StatusCode.*;

/**
 * @Description: 做差分包表格
 * @Author: jeecg-boot
 * @Date: 2021-05-27
 * @Version: V1.0
 */
@Service
public class DevopsDiffOtaServiceImpl extends ServiceImpl<DevopsDiffOtaMapper, DevopsDiffOta> implements IDevopsDiffOtaService, Job {
    @Autowired
    DevopsDiffOtaMapper devopsDiffOtaMapper;
    @Autowired
    DevopsServerMapper devopsServerMapper;

    private EventBus eventBus = null;

    private Random random = new Random();


    @Override
    public void handleOta(String id) {
        updateOtaStatus(id, OTA_STATUS__1);
        if (getServer().size() != 0) {
            autoOtaQueue();
        }
    }


    private void updateOtaStatus(String id, String OTA_STATUS) {
        devopsDiffOtaMapper.updateOtaStatus(id, OTA_STATUS);
    }

    private void updateOtaServerId(String id, String serverId) {
        devopsDiffOtaMapper.updateOtaServerId(id, serverId);
    }


    private List<DevopsServer> getServer() {
        return devopsServerMapper.queryOtaServerStatus(SERVER_USE_OTA, SERVER_STATUS_0);
    }

    private void autoOtaQueue() {
        List<DevopsServer> devopsServerList = getServer();
        if (devopsServerList.size() == 0) {
            return;
        }
        List<DevopsDiffOta> devopsDiffOtas = devopsDiffOtaMapper.queryOtaStatus(OTA_STATUS__1);
        if (devopsDiffOtas.size() == 0) {
            return;
        }
        System.out.println(devopsServerList.toString());
        System.out.println(devopsDiffOtas.toString());
        devopsServerMapper.updateServerStatus(SERVER_STATUS_3, devopsServerList.get(0).getId());
        String id = devopsDiffOtas.get(0).getId();
        updateOtaStatus(id, OTA_STATUS_2);
        DevopsDiffOta devopsDiffOta = getDiffOta(id);
        String ftpName, ftpNameOriginal, ftpPassWord = "", ftpOriginalPassWord = "";
        ftpName = CurlUtil.getFtpName(devopsDiffOta.getOtaNewDir());
        ftpNameOriginal = CurlUtil.getFtpName(devopsDiffOta.getOtaOriginalDir());

        if (CurlUtil.getFtpVerityDir(devopsDiffOta.getOtaOriginalDir())) {
            DevopsUpload devopsUpload = devopsDiffOtaMapper.queryUpload(ftpNameOriginal);
            if (devopsUpload != null) {
                ftpOriginalPassWord = devopsUpload.getUploadPassword();
            }
        } else {
            DevopsFtp devopsFtp = devopsDiffOtaMapper.querySign(ftpNameOriginal);
            if (devopsFtp != null) {
                ftpOriginalPassWord = devopsFtp.getFtpPassword();
            }
        }

        if (CurlUtil.getFtpVerityDir(devopsDiffOta.getOtaNewDir())) {
            DevopsUpload devopsUpload = devopsDiffOtaMapper.queryUpload(ftpName);
            if (devopsUpload != null) {
                ftpPassWord = devopsUpload.getUploadPassword();
            }
        } else {
            DevopsFtp devopsFtp = devopsDiffOtaMapper.querySign(ftpName);
            if (devopsFtp != null) {
                ftpPassWord = devopsFtp.getFtpPassword();
            }
        }
        System.out.println(ftpPassWord + "    " + ftpOriginalPassWord);
        if (StringUtil.isEmpty(ftpPassWord) || StringUtil.isEmpty(ftpOriginalPassWord)) {
            updateOtaStatus(id, OTA_STATUS_4);
            devopsServerMapper.updateServerStatus(SERVER_STATUS_0, devopsServerList.get(0).getId());
            return;
        }
        System.out.println(ftpPassWord + "    " + ftpOriginalPassWord);
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = CurlUtil.getActionOta(id, devopsDiffOta.getOtaOriginalDir(), ftpNameOriginal,
                ftpOriginalPassWord, devopsDiffOta.getOtaNewDir(), ftpName,
                ftpPassWord, devopsDiffOta.getOtaPlatform(), devopsServerList.get(0).getServerIp(),
                devopsServerList.get(0).getServerHost(), devopsServerList.get(0).getServerPassword());
        System.out.println(curldata + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};

        if (CurlUtil.run(cmds)) {
            devopsServerMapper.updateServerStatus(SERVER_STATUS_1, devopsServerList.get(0).getId());
            updateOtaServerId(id, devopsServerList.get(0).getId());
            autoOtaQueue();
        } else {
            devopsServerMapper.updateServerStatus(SERVER_STATUS_0, devopsServerList.get(0).getId());
            updateOtaStatus(id, OTA_STATUS_3);
        }
    }

    @Override
    public void setStatusDir(String id, String status, String otaDir, String otaLogUrl) {
        devopsDiffOtaMapper.updateOtaDir(id, status, otaDir, otaLogUrl);
        if (status.equals(OTA_STATUS_2)) {
            return;
        }
        DevopsDiffOta devopsDiffOta = getDiffOta(id);

        //编译结束，不管是有没有成功，服务器都置为空闲的
        devopsServerMapper.updateServerStatus(SERVER_STATUS_0, devopsDiffOta.getOtaServerId());

        String es_title = String.format(MessagesEnum.OTA_TITLE.getMsg(), devopsDiffOta.getOtaTaskId());
        String es_content;
        switch (status) {
            case OTA_STATUS_0:
                es_title += MessagesEnum.OTA_SUCCESS.getMsg();
                es_content = String.format(MessagesEnum.OTA_CONTENT.getMsg(), otaDir);
                break;
            case OTA_STATUS_3:
                es_title += MessagesEnum.OTA_ERROR.getMsg();
                es_content = MessagesEnum.OTA_CONTENT_FAIL.getMsg();
                break;
            case OTA_STATUS_4:
                es_title += MessagesEnum.OTA_CHECK_FAIL.getMsg();
                es_content = MessagesEnum.OTA_CHECK_FAIL.getMsg_detail();
                break;
            case OTA_STATUS_5:
                es_title += MessagesEnum.OTA_DOWNLOAD_FAIL.getMsg();
                es_content = MessagesEnum.OTA_DOWNLOAD_FAIL.getMsg_detail();
                break;
            case OTA_STATUS_6:
                es_title += MessagesEnum.OTA_PLATFORM_FAIL.getMsg();
                es_content = MessagesEnum.OTA_PLATFORM_FAIL.getMsg_detail();
                break;
            case OTA_STATUS_7:
                es_title += MessagesEnum.OTA_SYSTEM_FAIL.getMsg();
                es_content = MessagesEnum.OTA_SYSTEM_FAIL.getMsg_detail();
                break;
            case OTA_STATUS_8:
                es_title += MessagesEnum.OTA_UPLOAD_FAIL.getMsg();
                es_content = MessagesEnum.OTA_UPLOAD_FAIL.getMsg_detail();
                break;
            default:
                es_title += MessagesEnum.OTA_NONE.getMsg();
                es_content = MessagesEnum.OTA_NONE.getMsg_detail();
                break;

        }
        es_content = String.format(MessagesEnum.MESSAGE_HEADER.getMsg(), devopsDiffOta.getCreateBy())
                + es_content
                + MessagesEnum.MESSAGE_END.getMsg();
        String[] es_receiver = (devopsDiffOta.getOtaEmail()).split(",");

        EmailMessage emailMessage = new EmailMessage(es_receiver, es_title, es_content);
        if (eventBus == null) {
            eventBus = new EventBus();
            eventBus.register(new EventBusTools());
        }
        ChangeEvent event = new ChangeEvent(emailMessage);
        eventBus.post(event);
        autoOtaQueue();
    }

    @Override
    public void cancelOta(String id) {
        updateOtaStatus(id, OTA_STATUS_1);
    }

    @Override
    public Messages<?> handleCopy(String id) {
        Messages messages = new Messages<>(Resoure.Code.FAIL, Resoure.Message.get(Resoure.Code.FAIL), null);
        DevopsDiffOta devopsDiffOta = getDiffOta(id);
        System.out.println(devopsDiffOta.toString());
        if (devopsDiffOta != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            devopsDiffOtaMapper.insertHandleCopyOta((new Date()).getTime() + "" + random.nextInt(10000),
                    devopsDiffOta.getCreateBy(), df.format(new Date()).toString(), devopsDiffOta.getSysOrgCode(),
                    devopsDiffOta.getOtaOriginalDir(), devopsDiffOta.getOtaNewDir(),
                    devopsDiffOta.getOtaPlatform(), devopsDiffOta.getOtaEmail());
            messages.setMsg("复制成功");
        }
        return messages;
    }

    private DevopsDiffOta getDiffOta(String id) {
        return devopsDiffOtaMapper.queryOta(id);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
