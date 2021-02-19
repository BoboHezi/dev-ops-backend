package org.jeecg.modules.demo.devops.entity.resoure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public static String JENKINS_NAME = "admin";
    public static String JENKINS_NAME_PWD = "admin";
    public static String JENKINS_TOKEN;
    public static String JENKINS_BASE_URL;

    @Value("${config.jenkins_username}")
    public void setJenkinsName(String jenkinsName) {
        JENKINS_NAME = jenkinsName;
    }

    @Value("${config.jenkins_username_pwd}")
    public void setJenkinsNamePwd(String jenkinsNamePwd) {
        JENKINS_NAME_PWD = jenkinsNamePwd;
    }


    @Value("${config.jenkins_token}")
    public void setJenkinsToken(String jenkinsToken) {
        JENKINS_TOKEN = jenkinsToken;
    }

    @Value("${config.jenkins_url}")
    public void setJenkinsBaseUrl(String jenkinsBaseUrl) {
        Config.JENKINS_BASE_URL = jenkinsBaseUrl;
    }
}
