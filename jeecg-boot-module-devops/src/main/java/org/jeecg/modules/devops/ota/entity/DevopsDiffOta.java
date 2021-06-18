package org.jeecg.modules.devops.ota.entity;

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
 * @Description: 做差分包表格
 * @Author: jeecg-boot
 * @Date:   2021-05-27
 * @Version: V1.0
 */
@Data
@TableName("devops_diff_ota")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="devops_diff_ota对象", description="做差分包表格")
public class DevopsDiffOta implements Serializable {
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
	/**上个项目地址*/
	@Excel(name = "上个项目地址", width = 15)
    @ApiModelProperty(value = "上个项目地址")
    private java.lang.String otaOriginalDir;
	/**目标项目地址*/
	@Excel(name = "目标项目地址", width = 15)
    @ApiModelProperty(value = "目标项目地址")
    private java.lang.String otaNewDir;
	/**ota登录账号*/
	@Excel(name = "ota登录账号", width = 15)
    @ApiModelProperty(value = "ota登录账号")
    private java.lang.String otaUploadName;
	/**ota平台*/
	@Excel(name = "ota平台", width = 15)
    @ApiModelProperty(value = "ota平台")
    private java.lang.String otaPlatform;
	/**ota成功状态*/
	@Excel(name = "ota成功状态", width = 15)
    @ApiModelProperty(value = "ota成功状态")
    private java.lang.Integer otaStatus;
    /**邮件*/
    @Excel(name = "邮件", width = 15)
    @ApiModelProperty(value = "邮件")
    private java.lang.String otaEmail;
    /**原ota登录账号*/
    @Excel(name = "原ota登录账号", width = 15)
    @ApiModelProperty(value = "原ota登录账号")
    private java.lang.String otaOriginalUploadName;
    /**ota包的地址*/
    @Excel(name = "ota包的地址", width = 15)
    @ApiModelProperty(value = "ota包的地址")
    private java.lang.String otaSuccessDir;
    /**ota任务Id*/
    @Excel(name = "ota任务Id", width = 15)
    @ApiModelProperty(value = "ota任务Id")
    private java.lang.String otaTaskId;
}
