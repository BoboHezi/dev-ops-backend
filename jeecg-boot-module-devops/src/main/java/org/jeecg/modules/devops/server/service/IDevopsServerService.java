package org.jeecg.modules.devops.server.service;

import org.jeecg.modules.devops.server.entity.DevopsServer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
public interface IDevopsServerService extends IService<DevopsServer> {
    List<DevopsServer> getServerIPAll(String status);

    String handleRestart(String id);

    void handleAllRestart();

    void setStatusServer(String id,String status);

    void setStatusLinkServer(String id, String status, String statusLink, String serverDir, int serverLinkTimeEnd);

    void setStatusRefuseServer(String id,String statusLink);

    void setJenkinsLinkServer(String id, boolean stop_terminal);

    void sendServerEmail(String id, String status, String serverDir);
}
