package org.jeecg.modules.devops.compile.mapper;

import java.util.List;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.platform.entity.DevopsSignPlatform;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.sign.entity.DevopsSign;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
public interface DevopsCompileMapper extends BaseMapper<DevopsCompile> {

    /**
     * 根据状态查询所有服务器
     ** @return
     */
    public List<DevopsServer> queryServerStatus(@Param("status") String serverStatus,String platform,String codeStatus);
    /**
     * 根据条件查询地址
     ** @return
     */
    public DevopsCode queryCodeDir(@Param("status") String serverId, String platform);

    /**
     * 更新服务器状态
     */
    public void updateServerStatus(@Param("status") String serverStatus, String serverID);

    /**
     * 更新数据库
     ** @return
     */
    public void updateCompileStatus(@Param("status") String compileStatus, String compileID);

    /**
     * 查询ftp密码
     ** @return
     */
    DevopsFtp queryFtp(@Param("status") String id);

    /**
     * 查询网站地址
     ** @return
     */
    DevopsSign querySign(@Param("status") String id);



}
