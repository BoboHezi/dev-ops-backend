package org.jeecg.modules.devops.upload.controller;

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
import org.jeecg.modules.devops.upload.entity.DevopsUpload;
import org.jeecg.modules.devops.upload.service.IDevopsUploadService;

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
 * @Description: 验收账号表
 * @Author: jeecg-boot
 * @Date:   2021-05-27
 * @Version: V1.0
 */
@Api(tags="验收账号表")
@RestController
@RequestMapping("/upload/devopsUpload")
@Slf4j
public class DevopsUploadController extends JeecgController<DevopsUpload, IDevopsUploadService> {
	@Autowired
	private IDevopsUploadService devopsUploadService;
	
	/**
	 * 分页列表查询
	 *
	 * @param devopsUpload
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "验收账号表-分页列表查询")
	@ApiOperation(value="验收账号表-分页列表查询", notes="验收账号表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DevopsUpload devopsUpload,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DevopsUpload> queryWrapper = QueryGenerator.initQueryWrapper(devopsUpload, req.getParameterMap());
		Page<DevopsUpload> page = new Page<DevopsUpload>(pageNo, pageSize);
		IPage<DevopsUpload> pageList = devopsUploadService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param devopsUpload
	 * @return
	 */
	@AutoLog(value = "验收账号表-添加")
	@ApiOperation(value="验收账号表-添加", notes="验收账号表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DevopsUpload devopsUpload) {
		devopsUploadService.save(devopsUpload);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param devopsUpload
	 * @return
	 */
	@AutoLog(value = "验收账号表-编辑")
	@ApiOperation(value="验收账号表-编辑", notes="验收账号表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DevopsUpload devopsUpload) {
		devopsUploadService.updateById(devopsUpload);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "验收账号表-通过id删除")
	@ApiOperation(value="验收账号表-通过id删除", notes="验收账号表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devopsUploadService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "验收账号表-批量删除")
	@ApiOperation(value="验收账号表-批量删除", notes="验收账号表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.devopsUploadService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "验收账号表-通过id查询")
	@ApiOperation(value="验收账号表-通过id查询", notes="验收账号表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DevopsUpload devopsUpload = devopsUploadService.getById(id);
		if(devopsUpload==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(devopsUpload);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param devopsUpload
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsUpload devopsUpload) {
        return super.exportXls(request, devopsUpload, DevopsUpload.class, "验收账号表");
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
        return super.importExcel(request, response, DevopsUpload.class);
    }

}
