package org.jeecg.modules.devops.code.service.impl;

import org.apache.http.util.TextUtils;
import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.code.mapper.DevopsCodeMapper;
import org.jeecg.modules.devops.code.service.IDevopsCodeService;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 源代码管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsCodeServiceImpl extends ServiceImpl<DevopsCodeMapper, DevopsCode> implements IDevopsCodeService {

    @Autowired
    private DevopsCodeMapper devopsCodeMapper;

    @Override
    public String syncCode(String codeId) {
        Messages<?> messages = null;
        if (codeId.isEmpty()) {
            return Resoure.Message.get(Resoure.Code.FAIL);
        }
        devopsCodeMapper.SyncCode(codeId,"2");

        return Resoure.Message.get(Resoure.Code.SUCCESS);
    }
}
