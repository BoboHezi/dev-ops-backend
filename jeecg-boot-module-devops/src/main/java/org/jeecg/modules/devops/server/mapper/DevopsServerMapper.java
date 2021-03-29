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
    public List<DevopsServer> queryServerStatus(@Param("status") String status);

}
