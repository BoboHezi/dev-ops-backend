package org.jeecg.modules.devops.compile.service;

import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.devops.entity.Messages;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
public interface IDevopsCompileService extends IService<DevopsCompile> {
    Messages<?> autoCompile(DevopsCompile devopsCompile);
}
