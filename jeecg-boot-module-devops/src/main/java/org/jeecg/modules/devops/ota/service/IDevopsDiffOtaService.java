package org.jeecg.modules.devops.ota.service;

import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.ota.entity.DevopsDiffOta;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 做差分包表格
 * @Author: jeecg-boot
 * @Date:   2021-05-27
 * @Version: V1.0
 */
public interface IDevopsDiffOtaService extends IService<DevopsDiffOta> {
    void handleOta(String id);

    void setStatusDir(String id, String status, String otaDir,String otaLogUrl);

    void cancelOta(String id);

    Messages<?> handleCopy(String id);
}
