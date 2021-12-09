package org.jeecg.modules.devops.code.mapper;

import java.util.List;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 源代码管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
public interface DevopsCodeMapper extends BaseMapper<DevopsCode> {
    /**
     * 更新数据库
     * * @return
     */
    public void SyncCode(@Param("status") String id, String status);

}
