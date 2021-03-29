package org.jeecg.modules.devops.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.mapper.DevopsServerMapper;
import org.jeecg.modules.devops.server.service.IDevopsServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsServerServiceImpl extends ServiceImpl<DevopsServerMapper, DevopsServer> implements IDevopsServerService {
    @Autowired
    private DevopsServerMapper devopsServerMapper;

    @Override
    public List<DevopsServer> getServerIP(String status) {
        return devopsServerMapper.queryServerStatus(status);
    }
}
