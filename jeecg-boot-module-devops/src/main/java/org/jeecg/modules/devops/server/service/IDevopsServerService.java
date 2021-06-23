package org.jeecg.modules.devops.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    List<DevopsServer> getServerIP(String status);

    String handleRestart(String id);

    void handleAllRestart();

    void setStatusServer(String id,String status);
}
