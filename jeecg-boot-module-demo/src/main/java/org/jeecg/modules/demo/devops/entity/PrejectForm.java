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
 * @Description: 项目表单
 * @Author: jeecg-boot
 * @Date:   2021-02-19
 * @Version: V1.0
 */
@Data
@TableName("preject_form")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="preject_form对象", description="项目表单")
public class PrejectForm implements Serializable {
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
	/**项目平台*/
	@Excel(name = "项目平台", width = 15, dictTable = "platform_form", dicText = "platform_name", dicCode = "id")
	@Dict(dictTable = "platform_form", dicText = "platform_name", dicCode = "id")
    @ApiModelProperty(value = "项目平台")
    private java.lang.String projectPlatform;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    private java.lang.String projectName;
	/**服务器 IP 地址 */
	@Excel(name = "服务器 IP 地址 ", width = 15)
    @ApiModelProperty(value = "服务器 IP 地址 ")
    private java.lang.String serverIpaddress;
	/**版本类型*/
	@Excel(name = "版本类型", width = 15, dictTable = "devops_build", dicText = "build_variant", dicCode = "id")
	@Dict(dictTable = "devops_build", dicText = "build_variant", dicCode = "id")
    @ApiModelProperty(value = "版本类型")
    private java.lang.String projectVariant;
	/**编译签名*/
	@Excel(name = "编译签名", width = 15)
    @ApiModelProperty(value = "编译签名")
    private java.lang.String projectBuildSign;
	/**编译动作*/
	@Excel(name = "编译动作", width = 15, dictTable = "devops_build", dicText = "build_action", dicCode = "id")
	@Dict(dictTable = "devops_build", dicText = "build_action", dicCode = "id")
    @ApiModelProperty(value = "编译动作")
    private java.lang.String projectBuildAction;
	/**任务状态*/
	@Excel(name = "任务状态", width = 15)
    @ApiModelProperty(value = "任务状态")
    private java.lang.String projectStatus;

    @Override
    public String toString() {
        return "PrejectForm{" +
                "id='" + id + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", sysOrgCode='" + sysOrgCode + '\'' +
                ", projectPlatform='" + projectPlatform + '\'' +
                ", projectName='" + projectName + '\'' +
                ", serverIpaddress='" + serverIpaddress + '\'' +
                ", projectVariant='" + projectVariant + '\'' +
                ", projectBuildSign='" + projectBuildSign + '\'' +
                ", projectBuildAction='" + projectBuildAction + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
