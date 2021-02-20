package org.jeecg.modules.demo.devops.entity;

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
 * @Description: 服务器与平台
 * @Author: jeecg-boot
 * @Date:   2021-02-19
 * @Version: V1.0
 */
@Data
@TableName("servers_platform")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="servers_platform对象", description="服务器与平台")
public class ServersPlatform implements Serializable {
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
	/**服务器IP*/
	@Excel(name = "服务器IP", width = 15, dictTable = "servers_form", dicText = "servers_ip", dicCode = "id")
	@Dict(dictTable = "servers_form", dicText = "servers_ip", dicCode = "id")
    @ApiModelProperty(value = "服务器IP")
    private java.lang.String serversIp;
	/**平台名称*/
	@Excel(name = "平台名称", width = 15, dictTable = "platform_form", dicText = "platform_name", dicCode = "id")
	@Dict(dictTable = "platform_form", dicText = "platform_name", dicCode = "id")
    @ApiModelProperty(value = "平台名称")
    private java.lang.String serversPlatformName;
	/**平台路径*/
	@Excel(name = "平台路径", width = 15)
    @ApiModelProperty(value = "平台路径")
    private java.lang.String serversPlatformDir;
}