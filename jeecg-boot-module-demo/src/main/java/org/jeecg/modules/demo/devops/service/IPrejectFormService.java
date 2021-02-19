package org.jeecg.modules.demo.devops.service;

import org.jeecg.modules.demo.devops.entity.PrejectForm;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.devops.entity.respose.Messages;

/**
 * @Description: 项目表单
 * @Author: jeecg-boot
 * @Date:   2021-02-19
 * @Version: V1.0
 */
public interface IPrejectFormService extends IService<PrejectForm> {

    Messages<?> handleBuild(PrejectForm prejectForm);
}
