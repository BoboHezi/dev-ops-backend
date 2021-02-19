package org.jeecg.modules.demo.devops.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.devops.entity.PrejectForm;
import org.jeecg.modules.demo.devops.entity.resoure.Config;
import org.jeecg.modules.demo.devops.entity.resoure.Resoure;
import org.jeecg.modules.demo.devops.entity.respose.Messages;
import org.jeecg.modules.demo.devops.service.IPrejectFormService;
import org.jeecg.modules.demo.devops.utils.CurlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: 编译任务
* @Author: jianglingfeng
* @Date:   2021-02-19
* @Version: V1.0
*/
@Api(tags="编译任务")
@RestController
@RequestMapping("/devops/build")
@Slf4j
public class AutoBuildController extends JeecgController<PrejectForm, IPrejectFormService> {
   @Autowired
   private IPrejectFormService prejectFormService;
    /**
     * 自动编译
     *
     * @param prejectForm
     * @return
     */
    @AutoLog(value = "编译任务-自动编译")
    @ApiOperation(value = "编译任务-自动编译", notes = "编译任务-自动编译")
    @PostMapping(value = "/autoBuild")
    public Messages<?> autoBuild(HttpServletRequest request, @RequestBody PrejectForm prejectForm) {
        System.out.println("auto building ... ");

        Messages<?> messages;
        try {
            messages = prejectFormService.handleBuild(prejectForm);
        } catch (Exception e) {
            e.printStackTrace();
            messages = Messages.Error(e.getMessage());
        }
        return messages;
    }

}
