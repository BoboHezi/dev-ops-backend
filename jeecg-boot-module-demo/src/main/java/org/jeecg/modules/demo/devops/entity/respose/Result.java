package org.jeecg.modules.demo.devops.entity.respose;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jeecg.modules.demo.devops.entity.resoure.Resoure;

/**
 * 请求结果对象
 * @author liuchonglin
 *
 */
public class Result {
	private boolean success;
	private Integer code;
	private String  message;
	@JsonInclude(Include.NON_NULL)
	private Object  data;
	
	
	/**
	 * 成功返回
	 */
	public Result() {
		this.success = true;
		this.code    = Resoure.Code.SUCCESS;
		this.message = Resoure.Message.get(code);
	}
	
	/**
	 * 成功返回
	 */
	public Result(String message) {
		this.success = true;
		this.code    = Resoure.Code.SUCCESS;
		this.message = message;
	}
	
	/**
	 * 成功返回
	 * @param
	 */
	public Result(Object data) {
        this.success = true;
		this.code    = Resoure.Code.SUCCESS;
		this.message = Resoure.Message.get(code);
        this.data    = data;
    }
	
	/**
	 * 错误返回
	 * @param
	 * @param code
	 * @param message
	 */
	public Result(Integer code, String message) {
		this.success = false;
		this.code    = code;
		this.message = message;
	}
	
	/**
	 * 分页信息返回
	 * @return
	 */
	public Result(Object rows, Integer total) {
		this.success = true;
		this.code    = Resoure.Code.SUCCESS;
		this.message = Resoure.Message.get(code);
		this.data    = rows;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
