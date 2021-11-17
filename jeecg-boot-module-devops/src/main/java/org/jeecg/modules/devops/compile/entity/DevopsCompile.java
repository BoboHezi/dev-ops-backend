package org.jeecg.modules.devops.compile.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date:   2021-02-22
 * @Version: V1.0
 */
@Data
@TableName("devops_compile")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="devops_compile对象", description="编译管理")
public class DevopsCompile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String compileName;
    /**任务ID*/
    @Excel(name = "任务ID", width = 15)
    @ApiModelProperty(value = "任务ID")
    private java.lang.Integer compileBuildId;
    /**描述*/
    @Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String compileDesc;
    /**平台*/
    @Excel(name = "平台", width = 15)
    @ApiModelProperty(value = "平台")
    private java.lang.String compilePlatformId;
    /**新项目名*/
    @Excel(name = "新项目名", width = 15)
    @ApiModelProperty(value = "新项目名")
    private java.lang.String newCompileProject;
    /**项目，版本号*/
    @Excel(name = "项目，版本号", width = 15)
    @ApiModelProperty(value = "项目，版本号")
    private java.lang.String compileProjectId;
    /**编译服务器ip*/
    @Excel(name = "编译服务器ip", width = 15)
    @ApiModelProperty(value = "编译服务器ip")
    private java.lang.String compileServerIp;
    /**版本类型*/
    @Excel(name = "版本类型", width = 15)
    @ApiModelProperty(value = "版本类型")
    private java.lang.String compileVariant;
    /**编译动作*/
    @Excel(name = "编译动作", width = 15)
    @ApiModelProperty(value = "编译动作")
    private java.lang.String compileAction;
    /**是否签名*/
    @Excel(name = "是否签名", width = 15)
    @ApiModelProperty(value = "是否签名")
    private java.lang.String compileIsSign;
    /**是否验收*/
    @Excel(name = "是否验收", width = 15)
    @ApiModelProperty(value = "是否验收")
    private java.lang.String compileIsVerify;
    /**任务状态*/
    @Excel(name = "任务状态", width = 15)
    @ApiModelProperty(value = "任务状态")
    private java.lang.Integer compileStatus;
    /**编译日志*/
    @Excel(name = "编译日志", width = 15)
    @ApiModelProperty(value = "编译日志")
    private java.lang.String compileLogUrl;
    /**邮箱通知抄送*/
    @Excel(name = "邮箱通知抄送", width = 15)
    @ApiModelProperty(value = "邮箱通知抄送")
    private java.lang.String compileSendEmail;
    /**编译开始时间*/
    @Excel(name = "编译开始时间", width = 15, format = "yyyy-MM-dd HH:mm")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "编译开始时间")
    private java.util.Date compileBuildTime;
    /**编译结束时间*/
    @Excel(name = "编译结束时间", width = 15, format = "yyyy-MM-dd HH:mm")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "编译结束时间")
    private java.util.Date compileBuildFinishTime;
    /**job_name*/
    @Excel(name = "job_name", width = 15)
    @ApiModelProperty(value = "job_name")
    private java.lang.String compileJenkinsJobName;
    /**job_id*/
    @Excel(name = "job_id", width = 15)
    @ApiModelProperty(value = "job_id")
    private java.lang.String compileJenkinsJobId;
    /**签名ftp账号*/
    @Excel(name = "签名ftp账号", width = 15)
    @ApiModelProperty(value = "签名ftp账号")
    @Dict(dictTable = "devops_ftp", dicText = "ftp_user_name", dicCode = "id")
    private java.lang.String compileSignFtpId;
    /**登录签名后台账号*/
    @Excel(name = "登录签名后台账号", width = 15)
    @ApiModelProperty(value = "登录签名后台账号")
    @Dict(dictTable = "devops_sign", dicText = "sign_account", dicCode = "id")
    private java.lang.String compileLoginAccount;
    /**验收ftp账号*/
    @Excel(name = "验收ftp账号", width = 15)
    @ApiModelProperty(value = "验收ftp账号")
    private java.lang.String compileVerityFtpUserName;
    /**签名验收平台*/
    @Excel(name = "签名验收平台", width = 15)
    @ApiModelProperty(value = "签名验收平台")
    private java.lang.String compileSvPlatformTerrace;
    /**cherry_pick*/
    @Excel(name = "cherry_pick", width = 15)
    @ApiModelProperty(value = "cherry_pick")
    private java.lang.String cherryPick;
    /**compile_queue_level*/
    @Excel(name = "compile_queue_level", width = 15)
    @ApiModelProperty(value = "优先级")
    private java.lang.Integer compileQueueLevel;
    /**签名ftp地址*/
    @Excel(name = "签名ftp地址", width = 15)
    @ApiModelProperty(value = "签名ftp地址")
    private java.lang.String compileSignFtpUrl;
    /**签名任务id*/
    @Excel(name = "签名任务id", width = 15)
    @ApiModelProperty(value = "签名任务id")
    private java.lang.String compileSignId;
    /**验收任务id*/
    @Excel(name = "验收任务id", width = 15)
    @ApiModelProperty(value = "验收任务id")
    private java.lang.String compileVerityId;
    /**验收任务id*/
    @Excel(name = "保存Vmlinux", width = 15)
    @ApiModelProperty(value = "保存Vmlinux")
    private java.lang.String compileSaveVmlinux;
    /**验收任务id*/
    @Excel(name = "项目版本号", width = 15)
    @ApiModelProperty(value = "项目版本号")
    private java.lang.String compileProjectNum;
}
