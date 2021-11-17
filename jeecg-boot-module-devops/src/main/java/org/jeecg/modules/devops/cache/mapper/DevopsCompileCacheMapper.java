package org.jeecg.modules.devops.cache.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.devops.cache.entity.DevopsCompileCache;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 编译存放vmlinux
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
public interface DevopsCompileCacheMapper extends BaseMapper<DevopsCompileCache> {
    /**
     * 查询数据库CompileCache
     * * @return
     */
    DevopsCompileCache queryCompileCache(@Param("id") String id);

    List<DevopsCompileCache> queryCompileAllCache();
}
