package org.jeecg.modules.devops.code.service;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.devops.entity.Messages;

/**
 * @Description: 源代码管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
public interface IDevopsCodeService extends IService<DevopsCode> {

    String syncCode(String codeId);
}
