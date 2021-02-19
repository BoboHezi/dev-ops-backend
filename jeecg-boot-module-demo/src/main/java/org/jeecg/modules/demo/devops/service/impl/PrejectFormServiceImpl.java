package org.jeecg.modules.demo.devops.service.impl;

import org.apache.http.util.TextUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.devops.entity.PrejectForm;
import org.jeecg.modules.demo.devops.entity.resoure.Config;
import org.jeecg.modules.demo.devops.entity.resoure.Resoure;
import org.jeecg.modules.demo.devops.entity.respose.Messages;
import org.jeecg.modules.demo.devops.mapper.PrejectFormMapper;
import org.jeecg.modules.demo.devops.service.IPrejectFormService;
import org.jeecg.modules.demo.devops.utils.CurlUtil;
import org.jeecg.modules.demo.devops.utils.Md5Util;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 项目表单
 * @Author: jianglingfeng
 * @Date:   2021-02-19
 * @Version: V1.0
 */
@Service
public class PrejectFormServiceImpl extends ServiceImpl<PrejectFormMapper, PrejectForm> implements IPrejectFormService {

    @Override
    public Messages<?> handleBuild(PrejectForm prejectForm) {

        Messages<?> messages = null;
        if (prejectForm.getId() == null) {
            messages = new Messages<>(Resoure.Code.ID_NOT_EXIST, Resoure.Message.get(Resoure.Code.ID_NOT_EXIST), null);
            return messages;
        }

        System.out.println(prejectForm.toString()+"_________________________");

        if (TextUtils.isEmpty(prejectForm.getProjectStatus())) {

            String curldata = Config.JENKINS_NAME + ":" + Config.JENKINS_TOKEN;
            String curlurl = Config.JENKINS_BASE_URL
                    + "job/build_71p/buildWithParameters?project_name=" + prejectForm.getProjectName()
                    + "&server_ipaddress=" + prejectForm.getServerIpaddress()
                    + "&server_hostname=server"
                    + "&server_passwd=tyd1111"
                    + "&project_dir=/data/71p"
                    + "&repo_url=android-28/ALPS-P0-MP1-6762/freeme-9.1.0_prod-xt6771.xml"
                    + "&build_variant=" + prejectForm.getProjectVariant()
                    + "&build_type=" + prejectForm.getProjectBuildSign()
                    + "&build_action=" + prejectForm.getProjectBuildAction();
            System.out.println(curlurl);
            String[] cmds = {"curl", "-X", "POST", "--user", curldata, curlurl};
            if (CurlUtil.run(cmds)) {
                messages = new Messages<>(Resoure.Code.SUCCESS, Resoure.Message.get(Resoure.Code.SUCCESS), null);
            } else {
                messages = new Messages<>(Resoure.Code.CURL_RUN_FAIL, Resoure.Message.get(Resoure.Code.CURL_RUN_FAIL), null);
            }
        }

        return messages;
    }
}
