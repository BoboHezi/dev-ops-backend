package org.jeecg.modules.devops.server.entity;

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
 * @Date:   2021-02-22
 * @Version: V1.0
 */
@Data
@TableName("devops_server")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="devops_server对象", description="服务器表单")
public class DevopsServer implements Serializable {
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
    private java.lang.String serverName;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String serverDesc;
	/**IP地址*/
	@Excel(name = "IP地址", width = 15)
    @ApiModelProperty(value = "IP地址")
    private java.lang.String serverIp;
	/**主机名*/
	@Excel(name = "主机名", width = 15)
    @ApiModelProperty(value = "主机名")
    private java.lang.String serverHost;
	/**密码*/
	@Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "密码")
    private java.lang.String serverPassword;
	/**状态信息*/
	@Excel(name = "状态信息", width = 15)
    @ApiModelProperty(value = "状态信息")
    private java.lang.Integer serverStatus;
	/**CPU使用率*/
	@Excel(name = "CPU使用率", width = 15)
    @ApiModelProperty(value = "CPU使用率")
    private java.lang.String serverCpuUsageRate;
	/**存储剩余空间*/
	@Excel(name = "存储剩余空间", width = 15)
    @ApiModelProperty(value = "存储剩余空间")
    private java.lang.String serverAvailableStorage;
}
