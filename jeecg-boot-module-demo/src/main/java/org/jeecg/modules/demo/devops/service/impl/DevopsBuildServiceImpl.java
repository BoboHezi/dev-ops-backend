package org.jeecg.modules.demo.devops.service.impl;

import org.jeecg.modules.demo.devops.entity.DevopsBuild;
import org.jeecg.modules.demo.devops.mapper.DevopsBuildMapper;
import org.jeecg.modules.demo.devops.service.IDevopsBuildService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 编译类型
 * @Author: jeecg-boot
 * @Date:   2021-02-19
 * @Version: V1.0
 */
@Service
public class DevopsBuildServiceImpl extends ServiceImpl<DevopsBuildMapper, DevopsBuild> implements IDevopsBuildService {

}
