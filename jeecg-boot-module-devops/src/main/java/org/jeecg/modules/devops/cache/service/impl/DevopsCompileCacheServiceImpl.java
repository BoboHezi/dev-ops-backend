package org.jeecg.modules.devops.cache.service.impl;

import org.jeecg.modules.devops.cache.entity.DevopsCompileCache;
import org.jeecg.modules.devops.cache.mapper.DevopsCompileCacheMapper;
import org.jeecg.modules.devops.cache.service.IDevopsCompileCacheService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 编译存放vmlinux
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
@Service
public class DevopsCompileCacheServiceImpl extends ServiceImpl<DevopsCompileCacheMapper, DevopsCompileCache> implements IDevopsCompileCacheService {

}
