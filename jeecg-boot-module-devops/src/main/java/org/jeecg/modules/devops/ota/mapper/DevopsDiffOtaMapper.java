package org.jeecg.modules.devops.ota.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.ota.entity.DevopsDiffOta;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.sign.entity.DevopsSign;
import org.jeecg.modules.devops.upload.entity.DevopsUpload;

/**
 * @Description: 做差分包表格
 * @Author: jeecg-boot
 * @Date: 2021-05-27
 * @Version: V1.0
 */
public interface DevopsDiffOtaMapper extends BaseMapper<DevopsDiffOta> {


    /**
     * 查询数据库Ota
     * * @return
     */
    DevopsDiffOta queryOta(@Param("id") String id);

    DevopsUpload queryUpload(@Param("name") String name);

    DevopsFtp querySign(String name);

    void updateOtaServerId(String id, String serverId);

    void updateOtaStatus(@Param("id") String id, @Param("otaStatus") String otaStatus);

    List<DevopsDiffOta> queryOtaStatus(@Param("otaStatus") String otaStatus);

    void updateOtaDir(@Param("id") String id,String status, @Param("otaSuccessDir") String otaSuccessDir,String otaLogUrl);

    void insertHandleCopyOta(@Param("id") String id, String create_by, String create_time, String sys_org_code,
                             String ota_original_dir, String otaNewDir,
                             String otaPlatform, String otaEmail);

}
