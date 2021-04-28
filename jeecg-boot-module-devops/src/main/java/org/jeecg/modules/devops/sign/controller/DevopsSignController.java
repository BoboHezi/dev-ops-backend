package org.jeecg.modules.devops.sign.controller;

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
import org.jeecg.modules.devops.sign.entity.DevopsSign;
import org.jeecg.modules.devops.sign.service.IDevopsSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 登录签名网站
 * @Author: jeecg-boot
 * @Date:   2021-04-28
 * @Version: V1.0
 */
@Api(tags="登录签名网站")
@RestController
@RequestMapping("/sign/devopsSign")
@Slf4j
public class DevopsSignController extends JeecgController<DevopsSign, IDevopsSignService> {
	@Autowired
	private IDevopsSignService devopsSignService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devopsSign
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "登录签名网站-分页列表查询")
	@ApiOperation(value="登录签名网站-分页列表查询", notes="登录签名网站-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DevopsSign devopsSign,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DevopsSign> queryWrapper = QueryGenerator.initQueryWrapper(devopsSign, req.getParameterMap());
		Page<DevopsSign> page = new Page<DevopsSign>(pageNo, pageSize);
		IPage<DevopsSign> pageList = devopsSignService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devopsSign
	 * @return
	 */
	@AutoLog(value = "登录签名网站-添加")
	@ApiOperation(value="登录签名网站-添加", notes="登录签名网站-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevopsSign devopsSign) {
		devopsSignService.save(devopsSign);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devopsSign
	 * @return
	 */
	@AutoLog(value = "登录签名网站-编辑")
	@ApiOperation(value="登录签名网站-编辑", notes="登录签名网站-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevopsSign devopsSign) {
		devopsSignService.updateById(devopsSign);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "登录签名网站-通过id删除")
	@ApiOperation(value="登录签名网站-通过id删除", notes="登录签名网站-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devopsSignService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "登录签名网站-批量删除")
	@ApiOperation(value="登录签名网站-批量删除", notes="登录签名网站-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devopsSignService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "登录签名网站-通过id查询")
	@ApiOperation(value="登录签名网站-通过id查询", notes="登录签名网站-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DevopsSign devopsSign = devopsSignService.getById(id);
		if(devopsSign==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devopsSign);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param devopsSign
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsSign devopsSign) {
        return super.exportXls(request, devopsSign, DevopsSign.class, "登录签名网站");
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
        return super.importExcel(request, response, DevopsSign.class);
    }

}
