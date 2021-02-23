package org.jeecg.modules.devops.compile.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
	/**项目，平台，版本号*/
	@Excel(name = "项目，平台，版本号", width = 15)
    @ApiModelProperty(value = "项目，平台，版本号")
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
	@Excel(name = "编译开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "编译开始时间")
    private java.util.Date compileBuildTime;
	/**job_id*/
	@Excel(name = "job_id", width = 15)
    @ApiModelProperty(value = "job_id")
    private java.lang.String compileJenkinsJobId;
	
}
