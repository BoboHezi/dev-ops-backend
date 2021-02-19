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
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date:   2021-02-19
 * @Version: V1.0
 */
@Data
@TableName("servers_form")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="servers_form对象", description="服务器表单")
public class ServersForm implements Serializable {
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
	/**服务器ip*/
	@Excel(name = "服务器ip", width = 15)
    @ApiModelProperty(value = "服务器ip")
    private java.lang.String serversIp;
	/**主机名*/
	@Excel(name = "主机名", width = 15)
    @ApiModelProperty(value = "主机名")
    private java.lang.String serversHost;
	/**服务器密码*/
	@Excel(name = "服务器密码", width = 15)
    @ApiModelProperty(value = "服务器密码")
    private java.lang.String serversPassword;
	/**服务器平台*/
	@Excel(name = "服务器平台", width = 15)
    @ApiModelProperty(value = "服务器平台")
    private java.lang.String serversPlatform;
}
