package org.jeecg.modules.demo.devops.entity.resoure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
    public static String diver_name = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://192.168.1.23:3306/jeecg-boot242?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
    public static String username = "root";
    public static String password = "root";

    @Value("${spring.datasource.dynamic.datasource.master.driver-class-name}")
    public void setDiver_name(String diver_name) {
        DBConfig.diver_name = diver_name;
    }

    @Value("${spring.datasource.dynamic.datasource.master.url}")
    public void setUrl(String url) {
        DBConfig.url = url;
    }

    @Value("${spring.datasource.dynamic.datasource.master.username}")
    public void setUsername(String username) {
        DBConfig.username = username;
    }
    @Value("${spring.datasource.dynamic.datasource.master.password}")
    public void setPassword(String password) {
        DBConfig.password = password;
    }
}
