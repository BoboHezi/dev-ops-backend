package org.jeecg.modules.devops.cache.entity;

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
 * @Description: 编译存放vmlinux
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
@Data
@TableName("devops_compile_cache")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="devops_compile_cache对象", description="编译存放vmlinux")
public class DevopsCompileCache implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**jenkinsID*/
	@Excel(name = "jenkinsID", width = 15)
    @ApiModelProperty(value = "jenkinsID")
    private String jenkinsBuildId;
	/**编译任务ID*/
	@Excel(name = "编译任务ID", width = 15)
    @ApiModelProperty(value = "编译任务ID")
    private String devopsCompileId;
	/**项目名*/
	@Excel(name = "项目名", width = 15)
    @ApiModelProperty(value = "项目名")
    private String projectName;
	/**抄送邮件*/
	@Excel(name = "抄送邮件", width = 15)
    @ApiModelProperty(value = "抄送邮件")
    private String compileSendEmail;
	/**平台*/
	@Excel(name = "平台", width = 15)
    @ApiModelProperty(value = "平台")
    private String compilePlatformId;
	/**内部版本号*/
	@Excel(name = "内部版本号", width = 15)
    @ApiModelProperty(value = "内部版本号")
    private String versionInternal;
	/**文件存放路径*/
	@Excel(name = "文件存放路径", width = 15)
    @ApiModelProperty(value = "文件存放路径")
    private String cacheLocation;
}
