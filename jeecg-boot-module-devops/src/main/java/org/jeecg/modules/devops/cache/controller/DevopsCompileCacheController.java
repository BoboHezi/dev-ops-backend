package org.jeecg.modules.devops.cache.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.devops.cache.entity.DevopsCompileCache;
import org.jeecg.modules.devops.cache.service.IDevopsCompileCacheService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 编译存放vmlinux
 * @Author: jeecg-boot
 * @Date:   2021-09-07
 * @Version: V1.0
 */
@Api(tags="编译存放vmlinux")
@RestController
@RequestMapping("/cache/devopsCompileCache")
@Slf4j
public class DevopsCompileCacheController extends JeecgController<DevopsCompileCache, IDevopsCompileCacheService> {
	@Autowired
	private IDevopsCompileCacheService devopsCompileCacheService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devopsCompileCache
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "编译存放vmlinux-分页列表查询")
	@ApiOperation(value="编译存放vmlinux-分页列表查询", notes="编译存放vmlinux-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DevopsCompileCache devopsCompileCache,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DevopsCompileCache> queryWrapper = QueryGenerator.initQueryWrapper(devopsCompileCache, req.getParameterMap());
		Page<DevopsCompileCache> page = new Page<DevopsCompileCache>(pageNo, pageSize);
		IPage<DevopsCompileCache> pageList = devopsCompileCacheService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devopsCompileCache
	 * @return
	 */
	@AutoLog(value = "编译存放vmlinux-添加")
	@ApiOperation(value="编译存放vmlinux-添加", notes="编译存放vmlinux-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevopsCompileCache devopsCompileCache) {
		devopsCompileCacheService.save(devopsCompileCache);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devopsCompileCache
	 * @return
	 */
	@AutoLog(value = "编译存放vmlinux-编辑")
	@ApiOperation(value="编译存放vmlinux-编辑", notes="编译存放vmlinux-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevopsCompileCache devopsCompileCache) {
		devopsCompileCacheService.updateById(devopsCompileCache);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "编译存放vmlinux-通过id删除")
	@ApiOperation(value="编译存放vmlinux-通过id删除", notes="编译存放vmlinux-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devopsCompileCacheService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "编译存放vmlinux-批量删除")
	@ApiOperation(value="编译存放vmlinux-批量删除", notes="编译存放vmlinux-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devopsCompileCacheService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "编译存放vmlinux-通过id查询")
	@ApiOperation(value="编译存放vmlinux-通过id查询", notes="编译存放vmlinux-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DevopsCompileCache devopsCompileCache = devopsCompileCacheService.getById(id);
		if(devopsCompileCache==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devopsCompileCache);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param devopsCompileCache
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsCompileCache devopsCompileCache) {
        return super.exportXls(request, devopsCompileCache, DevopsCompileCache.class, "编译存放vmlinux");
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
        return super.importExcel(request, response, DevopsCompileCache.class);
    }

}
