package org.jeecg.modules.devops.server.service.impl;

import com.google.common.eventbus.EventBus;
import org.jeecg.modules.devops.compile.EventBusTools;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import org.jeecg.modules.devops.compile.mapper.DevopsCompileMapper;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.EmailMessage;
import org.jeecg.modules.devops.logholder.XLog;
import org.jeecg.modules.devops.messages.MessagesEnum;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.mapper.DevopsServerMapper;
import org.jeecg.modules.devops.server.service.IDevopsServerService;
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
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsServerServiceImpl extends ServiceImpl<DevopsServerMapper, DevopsServer> implements IDevopsServerService, Job {
    @Autowired
    private DevopsServerMapper devopsServerMapper;

    @Autowired
    private DevopsCompileMapper devopsCompileMapper;


    @Override
    public List<DevopsServer> getServerIPAll(String status) {
        return devopsServerMapper.queryServerStatusAll(status);
    }

    @Override
    public String handleRestart(String id) {
        DevopsServer devopsServer = getById(id);
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = CurlUtil.getRestartServer(id, devopsServer.getServerIp(), devopsServer.getServerHost(), devopsServer.getServerPassword());
        System.out.println(curlurl);
        setStatusServer(id, SERVER_STATUS_5);
        devopsServerMapper.updateServerPerformance(id,devopsServer.getServerPerformanceInitialValue());
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
            return "成功";
        } else {
            devopsServerMapper.updateServerStatus(SERVER_STATUS_0, id);
            return "失败";
        }
    }

    @Override
    public void handleAllRestart() {
        List<DevopsServer> devopsServers = getServerIPAll(SERVER_STATUS_0);
        for (DevopsServer devopsServer : devopsServers) {
            handleRestart(devopsServer.getId());
        }
    }

    @Override
    public void setStatusServer(String id, String status) {
        devopsServerMapper.updateServerStatus(status, id);
    }

    @Override
    public void setStatusLinkServer(String id, String status, String statusLink, String serverDir, int serverLinkTimeEnd) {
        devopsServerMapper.updateServerLinkStatus(status, statusLink, id, serverDir,serverLinkTimeEnd);
    }

    @Override
    public void setStatusRefuseServer(String id, String statusLink) {
        devopsServerMapper.updateServerRefuseStatus(statusLink, id);
    }

    @Override
    public void setJenkinsLinkServer(String id, boolean stop_terminal) {
        DevopsServer devopsServer = devopsServerMapper.queryServerId(id);
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = CurlUtil.getLinkServer(devopsServer.getServerIp(),
                devopsServer.getServerHost(), devopsServer.getServerPassword(), devopsServer.getId(), stop_terminal);
        System.out.println(curldata + "         " + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
        }

    }

    private EventBus eventBus = null;

    @Override
    public void sendServerEmail(String id, String status, String serverDir) {
        DevopsServer devopsServer = devopsServerMapper.queryServerId(id);
        if (!status.equals(SERVER_LINK_STATUS_1)) {
            if (devopsServer.getServerCompileId() != null && !StringUtil.isEmpty(devopsServer.getServerCompileId())) {
                DevopsCompile devopsCompile = devopsCompileMapper.queryCompile(devopsServer.getServerCompileId());
                String es_title ;
                String es_content;
                if (status.equals(SERVER_LINK_STATUS_0)) {
                    es_title = MessagesEnum.LINK_SERVER_SUCCESS.getMsg();
                    es_content = String.format(MessagesEnum.LINK_SERVER_CONTEXT.getMsg(), serverDir);
                } else {
                    es_title = MessagesEnum.LINK_SERVER_FAILED.getMsg();
                    es_content = MessagesEnum.LINK_SERVER_FAILED.getMsg_detail();
                }
                es_content = String.format(MessagesEnum.MESSAGE_HEADER.getMsg(), devopsCompile.getCreateBy())
                        + es_content
                        + MessagesEnum.MESSAGE_END.getMsg();

                String[] es_receiver = (devopsCompile.getCompileSendEmail()).split(",");

                EmailMessage emailMessage = new EmailMessage(es_receiver, es_title, es_content);
                if (eventBus == null) {
                    eventBus = new EventBus();
                    eventBus.register(new EventBusTools());
                }
                ChangeEvent event = new ChangeEvent(emailMessage);
                eventBus.post(event);
            }
        }
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            requestTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestTime() {
       List<DevopsServer> devopsServers = devopsServerMapper.queryServerLinkStatus(SERVER_LINK_STATUS_0);
       for (DevopsServer devopsServer :devopsServers){
           XLog.debug("devopsServers"+devopsServer.toString());
           if(devopsServer.getServerStopLinkTime() <= SERVER_LINK_TIME_START){
               setStatusLinkServer(devopsServer.getId(), SERVER_STATUS_7,SERVER_LINK_STATUS_4,"", SERVER_LINK_TIME_START);
               setJenkinsLinkServer(devopsServer.getId(),true);
           } else {
              devopsServerMapper.setServerLinkTime(devopsServer.getId(),devopsServer.getServerStopLinkTime()-1);
           }
       }

    }
}
