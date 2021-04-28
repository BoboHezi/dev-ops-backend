package org.jeecg.modules.devops.platform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.devops.platform.entity.DevopsSignPlatform;
import org.jeecg.modules.devops.platform.service.IDevopsSignPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 签名平台
 * @Author: jeecg-boot
 * @Date:   2021-04-28
 * @Version: V1.0
 */
@Api(tags="签名平台")
@RestController
@RequestMapping("/plat/devopsSignPlatform")
@Slf4j
public class DevopsSignPlatformController extends JeecgController<DevopsSignPlatform, IDevopsSignPlatformService> {
	@Autowired
	private IDevopsSignPlatformService devopsSignPlatformService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devopsSignPlatform
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "签名平台-分页列表查询")
	@ApiOperation(value="签名平台-分页列表查询", notes="签名平台-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DevopsSignPlatform devopsSignPlatform,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DevopsSignPlatform> queryWrapper = QueryGenerator.initQueryWrapper(devopsSignPlatform, req.getParameterMap());
		Page<DevopsSignPlatform> page = new Page<DevopsSignPlatform>(pageNo, pageSize);
		IPage<DevopsSignPlatform> pageList = devopsSignPlatformService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devopsSignPlatform
	 * @return
	 */
	@AutoLog(value = "签名平台-添加")
	@ApiOperation(value="签名平台-添加", notes="签名平台-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevopsSignPlatform devopsSignPlatform) {
		devopsSignPlatformService.save(devopsSignPlatform);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devopsSignPlatform
	 * @return
	 */
	@AutoLog(value = "签名平台-编辑")
	@ApiOperation(value="签名平台-编辑", notes="签名平台-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevopsSignPlatform devopsSignPlatform) {
		devopsSignPlatformService.updateById(devopsSignPlatform);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "签名平台-通过id删除")
	@ApiOperation(value="签名平台-通过id删除", notes="签名平台-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devopsSignPlatformService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "签名平台-批量删除")
	@ApiOperation(value="签名平台-批量删除", notes="签名平台-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devopsSignPlatformService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "签名平台-通过id查询")
	@ApiOperation(value="签名平台-通过id查询", notes="签名平台-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DevopsSignPlatform devopsSignPlatform = devopsSignPlatformService.getById(id);
		if(devopsSignPlatform==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devopsSignPlatform);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param devopsSignPlatform
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsSignPlatform devopsSignPlatform) {
        return super.exportXls(request, devopsSignPlatform, DevopsSignPlatform.class, "签名平台");
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
        return super.importExcel(request, response, DevopsSignPlatform.class);
    }

}
