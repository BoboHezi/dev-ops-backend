package org.jeecg.modules.devops.server.mapper;

import java.util.List;

import org.jeecg.modules.devops.server.entity.DevopsServer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
public interface DevopsServerMapper extends BaseMapper<DevopsServer> {
    /**
     * 根据状态查询所有服务器
     ** @return
     */
    public List<DevopsServer> queryServerStatusAll(@Param("status") String status);

    DevopsServer queryServerId(String id);

    List<DevopsServer> queryServerLinkStatus(String serverLinkStatus);

    void setServerLinkTime(String id,int linkTime);

    /**
     * 更新服务器状态
     */
    public void updateServerStatus(@Param("status") String serverStatus, String serverID);

    List<DevopsServer> queryOtaServerStatus(@Param("server_status") String server_use_flag, String server_status);

    /**
     * 更新服务器状态
     */
    public void updateServerLinkStatus(@Param("status") String serverStatus, String serverLinkStatus, String serverID, String serverDir, int serverLinkTimeEnd);

    /**
     * 更新服务器状态
     */
    public void updateServerRefuseStatus(@Param("status") String serverLinkStatus, String serverID);

    /**
     * 请求服务器
     */
    void updateRequestServer(String id, String serverIp);

    DevopsServer queryRequestServer(String serverIp);

    /**
     * 根据状态查询所有服务器
     * * @return
     */
    public List<DevopsServer> queryServerStatus(@Param("status") String serverStatus, String platform, String codeStatus);

    /**
     * 根据状态查询单个服务器
     * * @return
     */
    public List<DevopsServer> queryServerCodeAndIp(@Param("status") String platform, String serverIp, String codeStatus);

    /**
     * 根据状态查询单个服务器
     * * @return
     */
    public List<DevopsServer> queryServerStatusAndIp(@Param("status") String serverStatus, String platform, String serverIp, String codeStatus);

    /**
     * 根据状态查询所有服务器
     * * @return
     */
    public List<DevopsServer> queryServerCode(@Param("status") String platform, String codeStatus);

    void updateServerPerformance(String id, int performance);


}
