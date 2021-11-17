package org.jeecg.modules.devops.compile.mapper;

import java.util.List;

import org.jeecg.modules.devops.code.entity.DevopsCode;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.platform.entity.DevopsSignPlatform;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.sign.entity.DevopsSign;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
public interface DevopsCompileMapper extends BaseMapper<DevopsCompile> {

    /**
     * 查询数据库Compile
     * * @return
     */
    DevopsCompile queryCompile(@Param("status") String id);

    /**
     * * @return
     */
    void insertHandleCopy(@Param("status") String id, String create_by, String create_time, String sys_org_code, String compile_name, String compile_desc,
                          String compile_platform_id, String new_compile_project, String compile_project_id, String compile_server_ip,
                          String compile_variant, String compile_action, String compile_is_sign, String compile_is_verify,
                          String compile_send_email, String compile_sign_ftp_id, String compile_login_account, String compile_verity_ftp_user_name,
                          String compile_sv_platform_terrace, String cherry_pick);


    /**
     * 根据条件查询地址
     * * @return
     */
    public DevopsCode queryCodeDir(@Param("status") String serverId, String platform);



    /**
     * 更新数据库
     * * @return
     */
    public void updateCompileStatus(@Param("status") String compileStatus, String compileID);

    /**
     * 查询ftp密码
     * * @return
     */
    DevopsFtp queryFtp(@Param("status") String id);

    /**
     * 查询网站地址
     * * @return
     */
    DevopsSign querySign(@Param("status") String signAccount);

    /**
     * 查找排队编译的项目
     * * @return
     */
    List<DevopsCompile> queryCompileQueue(@Param("status") String compileStatus);

    /**
     * 更新编译项目的优先级
     * * @return
     */
    void updateCompileQueueLevel(@Param("status") String id, String Level);

    /**
     * 清除数据 jenkins
     */
    void clearJenkinsData(String id);




}
