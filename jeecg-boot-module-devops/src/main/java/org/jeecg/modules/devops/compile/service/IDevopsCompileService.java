package org.jeecg.modules.devops.compile.service;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.server.entity.DevopsServer;

import java.util.List;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
public interface IDevopsCompileService extends IService<DevopsCompile> {

    Messages<?> autoCompile(DevopsCompile devopsCompile);

    Messages<?> stopCompile(DevopsCompile devopsCompile);

    List<DevopsServer> getServerIP(String serverStatus,String platform,String codeStatus);

    DevopsCode getCodeDir(String serverId, String platform);

    Messages<?> checkLog(DevopsCompile devopsCompile);
}
