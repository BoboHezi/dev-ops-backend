package org.jeecg.modules.devops.server.service.impl;

import org.jeecg.modules.devops.compile.mapper.DevopsCompileMapper;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.mapper.DevopsServerMapper;
import org.jeecg.modules.devops.server.service.IDevopsServerService;
import org.jeecg.modules.devops.utils.CurlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import static org.jeecg.modules.devops.StatusCode.*;

/**
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsServerServiceImpl extends ServiceImpl<DevopsServerMapper, DevopsServer> implements IDevopsServerService {
    @Autowired
    private DevopsServerMapper devopsServerMapper;
    @Autowired
    private DevopsCompileMapper devopsCompileMapper;

    @Override
    public List<DevopsServer> getServerIP(String status) {
        return devopsServerMapper.queryServerStatus(status);
    }

    @Override
    public String handleRestart(String id) {
        DevopsServer devopsServer = getById(id);
        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN + "@";
        String curlurl = CurlUtil.getRestartServer(id, devopsServer.getServerIp(), devopsServer.getServerHost(), devopsServer.getServerPassword());
        System.out.println(curlurl);
        devopsCompileMapper.updateServerStatus(SERVER_STATUS_5, id);
        String[] cmds = {"curl", "-X", "POST", "http://" + curldata + curlurl};
        if (CurlUtil.run(cmds)) {
            return "成功";
        } else {
            devopsCompileMapper.updateServerStatus(SERVER_STATUS_0, id);
            return "失败";
        }
    }

    @Override
    public void handleAllRestart() {
        List<DevopsServer> devopsServers = getServerIP(SERVER_STATUS_0);
        for (DevopsServer devopsServer : devopsServers) {
            handleRestart(devopsServer.getId());
        }
    }
}
