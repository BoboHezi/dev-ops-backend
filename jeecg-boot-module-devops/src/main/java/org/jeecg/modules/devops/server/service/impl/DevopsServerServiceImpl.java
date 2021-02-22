package org.jeecg.modules.devops.server.service.impl;

import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.mapper.DevopsServerMapper;
import org.jeecg.modules.devops.server.service.IDevopsServerService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsServerServiceImpl extends ServiceImpl<DevopsServerMapper, DevopsServer> implements IDevopsServerService {

}
