package org.jeecg.modules.devops.compile.service.impl;

import org.apache.http.util.TextUtils;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import org.jeecg.modules.devops.compile.mapper.DevopsCompileMapper;
import org.jeecg.modules.devops.compile.service.IDevopsCompileService;
import org.jeecg.modules.devops.entity.Config;
import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.modules.devops.entity.Resoure;
import org.jeecg.modules.devops.utils.CurlUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Service
public class DevopsCompileServiceImpl extends ServiceImpl<DevopsCompileMapper, DevopsCompile> implements IDevopsCompileService {

    @Override
    public Messages<?> autoCompile(DevopsCompile devopsCompile) {

        Messages<?> messages = null;
        if (devopsCompile.getId() == null) {
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }

        System.out.println(devopsCompile.toString() + "_________________________");

        String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN;
        String curlurl = Config.JENKINS_BASE_URL
                + "job/build_71p/buildWithParameters?project_name=" + devopsCompile.getCompileName()
                + "&server_ipaddress=" + devopsCompile.getCompileServerIp()
//                    + "&server_hostname=" + devopsCompile.getServerHost()
//                    + "&server_passwd=" + devopsCompile.getServerPassword()
//                    + "&project_dir=" + devopsCompile.getProjectDir()
//                + "&repo_url=android-28/ALPS-P0-MP1-6762/freeme-9.1.0_prod-xt6771.xml"
                + "&build_variant=" + devopsCompile.getCompileVariant()
                + "&build_type=" + devopsCompile.getCompileIsSign()
                + "&build_action=" + devopsCompile.getCompileAction();
        System.out.println(curlurl);
        String[] cmds = {"curl", "-X", "POST", "--user", curldata, curlurl};
        if (CurlUtil.run(cmds)) {
            messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
        } else {
            messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
        }


        return messages;
    }
}
