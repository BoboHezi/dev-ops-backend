package org.jeecg.modules.devops.ota.service.impl;

import com.google.common.eventbus.EventBus;
import org.jeecg.modules.devops.compile.EventBusTools;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.EmailMessage;
import org.jeecg.modules.devops.messages.MessagesEnum;
import org.jeecg.modules.devops.ota.entity.DevopsDiffOta;
import org.jeecg.modules.devops.ota.mapper.DevopsDiffOtaMapper;
import org.jeecg.modules.devops.ota.service.IDevopsDiffOtaService;
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
import java.util.List;

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

    private EventBus eventBus = null;


    @Override
    public void handleOta(String id) {
        devopsDiffOtaMapper.updateOtaStatus(id, OTA_STATUS__1);
        if (devopsDiffOtaMapper.queryOtaStatus(OTA_STATUS_2).size() == 0) {
            autoOtaQueue();
        }
    }

    private void autoOtaQueue() {
        System.out.println("ota 排队");
        List<DevopsDiffOta> devopsDiffOtas = devopsDiffOtaMapper.queryOtaStatus(OTA_STATUS__1);
        if (devopsDiffOtas.size() == 0) {
            return;
        }
        String id = devopsDiffOtas.get(0).getId();
        devopsDiffOtaMapper.updateOtaStatus(id, OTA_STATUS_2);
        DevopsDiffOta devopsDiffOta = devopsDiffOtaMapper.queryOta(id);
        DevopsUpload devopsUpload;
        DevopsUpload devopsUploadOriginal = devopsDiffOtaMapper.queryUpload(devopsDiffOta.getOtaOriginalUploadName());
        if (devopsDiffOta.getOtaOriginalUploadName().equals(devopsDiffOta.getOtaUploadName())) {
            devopsUpload = devopsUploadOriginal;
        } else {
            devopsUpload = devopsDiffOtaMapper.queryUpload(devopsDiffOta.getOtaUploadName());
        }
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";

        if(StringUtil.isEmpty(devopsDiffOta.getOtaOriginalDir()) || StringUtil.isEmpty(devopsDiffOta.getOtaNewDir())){
            return;
        }

        String curlurl = CurlUtil.getActionOta(id, devopsDiffOta.getOtaOriginalDir(), devopsDiffOta.getOtaOriginalUploadName(),
                devopsUploadOriginal.getUploadPassword(), devopsDiffOta.getOtaNewDir(), devopsDiffOta.getOtaUploadName(),
                devopsUpload.getUploadPassword(), devopsDiffOta.getOtaPlatform());
        System.out.println(curldata + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (!CurlUtil.run(cmds)) {
            devopsDiffOtaMapper.updateOtaStatus(id, OTA_STATUS_3);
        }
    }

    @Override
    public void setStatusDir(String id, String status, String otaDir) {
        devopsDiffOtaMapper.updateOtaStatus(id, status);
        devopsDiffOtaMapper.updateOtaDir(id, otaDir);
        DevopsDiffOta devopsDiffOta = devopsDiffOtaMapper.queryOta(id);

        String es_title = String.format(MessagesEnum.OTA_TITLE.getMsg(), devopsDiffOta.getOtaTaskId());
        String es_content;
        if (status.equals(OTA_STATUS_0)) {
            es_title += MessagesEnum.OTA_SUCCESS.getMsg();
            es_content = String.format(MessagesEnum.OTA_CONTENT.getMsg(), otaDir);
        } else {
            es_title += MessagesEnum.OTA_ERROR.getMsg();
            es_content = MessagesEnum.OTA_CONTENT_FAIL.getMsg();
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
    }

    @Override
    public void cancelOta(String id) {
        devopsDiffOtaMapper.updateOtaStatus(id, OTA_STATUS_1);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            autoOtaQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
