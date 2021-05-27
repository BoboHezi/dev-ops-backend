package org.jeecg.modules.devops.compile.service;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.platform.entity.DevopsSignPlatform;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.sign.entity.DevopsSign;

import java.util.List;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
public interface IDevopsCompileService extends IService<DevopsCompile> {

    Messages<?> autoCompile(DevopsCompile devopsCompile);

    Messages<?> stopCompile(DevopsCompile devopsCompile);

    List<DevopsServer> getServerIP(String serverStatus, String platform, String serverIp, String codeStatus);

    List<DevopsServer> getServerCode(String platform, String serverIp, String codeStatus);

    DevopsCode getCodeDir(String serverId, String platform);

    Messages<?> handleCopy(String id);

    DevopsCompile selectCompile(String id);

    Messages<?> checkLog(DevopsCompile devopsCompile);

    void setServerStatus(String serverStatus, String serverID);

    void autoCompileQueue();

    void setCompileStatus(String compileStatus, String compileID);

    DevopsFtp getFtp(String id);

    DevopsSign getSign(String id);

    List<DevopsCompile> getCompileQueue(String compileStatus);

    void setCompileQueueLevel(String id, String Level);

    void cancelCompile(String id, String compileStatus);
}
