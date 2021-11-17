package org.jeecg.modules.devops.ftp.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.devops.ftp.entity.DevopsFtp;
import org.jeecg.modules.devops.ftp.service.IDevopsFtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: ftp列表
 * @Author: jeecg-boot
 * @Date:   2021-04-27
 * @Version: V1.0
 */
@Api(tags="ftp列表")
@RestController
@RequestMapping("/devops/devopsFtp")
@Slf4j
public class DevopsFtpController extends JeecgController<DevopsFtp, IDevopsFtpService> {
	@Autowired
	private IDevopsFtpService devopsFtpService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devopsFtp
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ftp列表-分页列表查询")
	@ApiOperation(value="ftp列表-分页列表查询", notes="ftp列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DevopsFtp devopsFtp,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DevopsFtp> queryWrapper = QueryGenerator.initQueryWrapper(devopsFtp, req.getParameterMap());
		Page<DevopsFtp> page = new Page<DevopsFtp>(pageNo, pageSize);
		IPage<DevopsFtp> pageList = devopsFtpService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devopsFtp
	 * @return
	 */
	@AutoLog(value = "ftp列表-添加")
	@ApiOperation(value="ftp列表-添加", notes="ftp列表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevopsFtp devopsFtp) {
		devopsFtpService.save(devopsFtp);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devopsFtp
	 * @return
	 */
	@AutoLog(value = "ftp列表-编辑")
	@ApiOperation(value="ftp列表-编辑", notes="ftp列表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevopsFtp devopsFtp) {
		devopsFtpService.updateById(devopsFtp);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ftp列表-通过id删除")
	@ApiOperation(value="ftp列表-通过id删除", notes="ftp列表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devopsFtpService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ftp列表-批量删除")
	@ApiOperation(value="ftp列表-批量删除", notes="ftp列表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devopsFtpService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ftp列表-通过id查询")
	@ApiOperation(value="ftp列表-通过id查询", notes="ftp列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DevopsFtp devopsFtp = devopsFtpService.getById(id);
		if(devopsFtp==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devopsFtp);
	}

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "ftp列表-通过id查询")
	 @ApiOperation(value="ftp列表-通过id查询", notes="ftp列表-通过id查询")
	 @GetMapping(value = "/queryByIdAccount")
	 public Result<?> queryByIdAccount(@RequestParam(name="id",required=true) String id) {
		 DevopsFtp devopsFtp = devopsFtpService.getById(id);
		 if(devopsFtp==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(devopsFtp.getFtpUserName());
	 }


	 /**
    * 导出excel
    *
    * @param request
    * @param devopsFtp
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsFtp devopsFtp) {
        return super.exportXls(request, devopsFtp, DevopsFtp.class, "ftp列表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DevopsFtp.class);
    }

}
