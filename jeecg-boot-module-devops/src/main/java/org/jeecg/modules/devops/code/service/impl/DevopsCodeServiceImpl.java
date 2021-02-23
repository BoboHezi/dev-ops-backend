package org.jeecg.modules.devops.code.service.impl;

import org.apache.http.util.TextUtils;
import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.code.mapper.DevopsCodeMapper;
import org.jeecg.modules.devops.code.service.IDevopsCodeService;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
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

    @Override
    public Messages<?> syncCode(DevopsCode devopsCode) {
        Messages<?> messages = null;
        if (devopsCode.getId() == null) {
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }

        System.out.println(devopsCode.toString()+"_________________________");

//        if (TextUtils.isEmpty(prejectForm.getProjectStatus())) {
//
//            String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN;
//            String curlurl = Config.JENKINS_BASE_URL
//                    + "job/build_71p/buildWithParameters?project_name=" + prejectForm.getProjectName()
//                    + "&server_ipaddress=" + prejectForm.getServerIpaddress()
//                    + "&server_hostname=" + prejectForm.getServerHost()
//                    + "&server_passwd=" + prejectForm.getServerPassword()
//                    + "&project_dir=" + prejectForm.getProjectDir()
//                    + "&repo_url=android-28/ALPS-P0-MP1-6762/freeme-9.1.0_prod-xt6771.xml"
//                    + "&build_variant=" + prejectForm.getProjectVariant()
//                    + "&build_type=" + prejectForm.getProjectBuildSign()
//                    + "&build_action=" + prejectForm.getProjectBuildAction();
//            System.out.println(curlurl);
//            String[] cmds = {"curl", "-X", "POST", "--user", curldata, curlurl};
//            if (CurlUtil.run(cmds)) {
//                messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
//            } else {
//                messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
//            }
//        }
        return messages;
    }
}
