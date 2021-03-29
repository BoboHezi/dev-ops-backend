package org.jeecg.modules.devops.compile.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.http.util.TextUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import org.jeecg.modules.devops.compile.mapper.DevopsCompileMapper;
import org.jeecg.modules.devops.compile.service.IDevopsCompileService;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
import org.jeecg.modules.devops.server.controller.DevopsServerController;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.service.impl.DevopsServerServiceImpl;
import org.jeecg.modules.devops.utils.CurlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsCompileServiceImpl extends ServiceImpl<DevopsCompileMapper, DevopsCompile> implements IDevopsCompileService {
    @Autowired
    private DevopsCompileMapper devopsCompileMapper;

    @Override
    public List<DevopsServer> getServerIP(String serverStatus, String platform, String codeStatus) {
        return devopsCompileMapper.queryServerStatus(serverStatus, platform, codeStatus);
    }

    @Override
    public DevopsCode getCodeDir(String serverId, String platform) {
        return devopsCompileMapper.queryCodeDir(serverId, platform);
    }

    /*查看log*/
    @Override
    public Messages<?> checkLog(DevopsCompile devopsCompile) {
        Messages messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
        if(devopsCompile.getCompileLogUrl()!=null){
            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
            return messages;
        }
        return messages;
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

        System.out.println(devopsCompile);

        List<DevopsServer> devopsServersList = getServerIP("0", devopsCompile.getCompilePlatformId(), "2");
        System.out.println("服务器List —————————"+devopsServersList);

        /*
        * 当没有可以用的服务器退出
        * */
        if (devopsServersList.size() == 0) {
            return messages;
        }
        devopsServer = devopsServersList.get(0);
        devopsCode = getCodeDir(devopsServer.getId(), devopsCompile.getCompilePlatformId());
        String compileProjectName =devopsCompile.getCompileProjectId();
        if(compileProjectName == null){
            compileProjectName = devopsCompile.getNewCompileProject();
        }
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
                + "&is_new_project=" + "false";
        System.out.println(curldata  + curlurl);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
        } else {
            messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
        }
        return messages;
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
                + "job_name_extra="+devopsCompile.getCompileJenkinsJobName()
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
}
