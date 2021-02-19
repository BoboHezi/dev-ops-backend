package org.jeecg.modules.demo.devops.controller;

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
import org.jeecg.modules.demo.devops.entity.DevopsBuild;
import org.jeecg.modules.demo.devops.service.IDevopsBuildService;

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
 * @Description: 编译类型
 * @Author: jeecg-boot
 * @Date:   2021-02-19
 * @Version: V1.0
 */
@Api(tags="编译类型")
@RestController
@RequestMapping("/devops/devopsBuild")
@Slf4j
public class DevopsBuildController extends JeecgController<DevopsBuild, IDevopsBuildService> {
	@Autowired
	private IDevopsBuildService devopsBuildService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devopsBuild
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "编译类型-分页列表查询")
	@ApiOperation(value="编译类型-分页列表查询", notes="编译类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DevopsBuild devopsBuild,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DevopsBuild> queryWrapper = QueryGenerator.initQueryWrapper(devopsBuild, req.getParameterMap());
		Page<DevopsBuild> page = new Page<DevopsBuild>(pageNo, pageSize);
		IPage<DevopsBuild> pageList = devopsBuildService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devopsBuild
	 * @return
	 */
	@AutoLog(value = "编译类型-添加")
	@ApiOperation(value="编译类型-添加", notes="编译类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevopsBuild devopsBuild) {
		devopsBuildService.save(devopsBuild);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devopsBuild
	 * @return
	 */
	@AutoLog(value = "编译类型-编辑")
	@ApiOperation(value="编译类型-编辑", notes="编译类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevopsBuild devopsBuild) {
		devopsBuildService.updateById(devopsBuild);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "编译类型-通过id删除")
	@ApiOperation(value="编译类型-通过id删除", notes="编译类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devopsBuildService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "编译类型-批量删除")
	@ApiOperation(value="编译类型-批量删除", notes="编译类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devopsBuildService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "编译类型-通过id查询")
	@ApiOperation(value="编译类型-通过id查询", notes="编译类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DevopsBuild devopsBuild = devopsBuildService.getById(id);
		if(devopsBuild==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devopsBuild);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param devopsBuild
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsBuild devopsBuild) {
        return super.exportXls(request, devopsBuild, DevopsBuild.class, "编译类型");
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
        return super.importExcel(request, response, DevopsBuild.class);
    }

}
