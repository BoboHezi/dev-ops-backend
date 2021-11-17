package org.jeecg.modules.devops.ota.controller;

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
import org.jeecg.modules.devops.messages.handle.impl.EmailSendMsgHandle;
import org.jeecg.modules.devops.ota.entity.DevopsDiffOta;
import org.jeecg.modules.devops.ota.service.IDevopsDiffOtaService;

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
 * @Description: 做差分包表格
 * @Author: jeecg-boot
 * @Date: 2021-05-27
 * @Version: V1.0
 */
@Api(tags = "做差分包表格")
@RestController
@RequestMapping("/ota/devopsDiffOta")
@Slf4j
public class DevopsDiffOtaController extends JeecgController<DevopsDiffOta, IDevopsDiffOtaService> {
    @Autowired
    private IDevopsDiffOtaService devopsDiffOtaService;

    /**
     * 分页列表查询
     *
     * @param devopsDiffOta
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "做差分包表格-分页列表查询")
    @ApiOperation(value = "做差分包表格-分页列表查询", notes = "做差分包表格-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DevopsDiffOta devopsDiffOta,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DevopsDiffOta> queryWrapper = QueryGenerator.initQueryWrapper(devopsDiffOta, req.getParameterMap());
        Page<DevopsDiffOta> page = new Page<DevopsDiffOta>(pageNo, pageSize);
        IPage<DevopsDiffOta> pageList = devopsDiffOtaService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param devopsDiffOta
     * @return
     */
    @AutoLog(value = "做差分包表格-添加")
    @ApiOperation(value = "做差分包表格-添加", notes = "做差分包表格-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevopsDiffOta devopsDiffOta) {
        devopsDiffOtaService.save(devopsDiffOta);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devopsDiffOta
     * @return
     */
    @AutoLog(value = "做差分包表格-编辑")
    @ApiOperation(value = "做差分包表格-编辑", notes = "做差分包表格-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevopsDiffOta devopsDiffOta) {
        devopsDiffOtaService.updateById(devopsDiffOta);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "做差分包表格-通过id删除")
    @ApiOperation(value = "做差分包表格-通过id删除", notes = "做差分包表格-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        devopsDiffOtaService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "做差分包表格-批量删除")
    @ApiOperation(value = "做差分包表格-批量删除", notes = "做差分包表格-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.devopsDiffOtaService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "做差分包表格-通过id查询")
    @ApiOperation(value = "做差分包表格-通过id查询", notes = "做差分包表格-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DevopsDiffOta devopsDiffOta = devopsDiffOtaService.getById(id);
        if (devopsDiffOta == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(devopsDiffOta);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param devopsDiffOta
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsDiffOta devopsDiffOta) {
        return super.exportXls(request, devopsDiffOta, DevopsDiffOta.class, "做差分包表格");
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
        return super.importExcel(request, response, DevopsDiffOta.class);
    }

    /**
     * 发送邮件
     *
     * @param
     * @return
     */
    @AutoLog(value = "setJenkinsOtaStatus")
    @ApiOperation(value = "发送邮件", notes = "发送邮件")
    @GetMapping(value = "/setJenkinsOtaStatus")
    public Result<?> setJenkinsOtaStatus(@RequestParam(name = "id", required = true) String id,
                                         @RequestParam(name = "status", required = true) String status,
                                         @RequestParam(name = "otaDir", required = true) String otaDir,
                                         @RequestParam(name = "otaLogUrl", required = true) String otaLogUrl
                                         ) {
        devopsDiffOtaService.setStatusDir(id,status,otaDir,otaLogUrl);
        return Result.OK("SUCCESS");
    }

    /**
     * 取消排队
     *
     * @param
     * @return
     */
    @AutoLog(value = "制作ota")
    @ApiOperation(value = "制作ota", notes = "制作ota")
    @GetMapping(value = "/handleOta")
    public Result<?> handleOta(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        devopsDiffOtaService.handleOta(id);
        return Result.OK("正在制作","");
    }

    /**
     * 复制OTA
     *
     * @param
     * @return
     */
    @AutoLog(value = "复制OTA")
    @ApiOperation(value = "复制OTA", notes = "复制OTA")
    @GetMapping(value = "/handleCopy")
    public Result<?> handleCopy(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        return Result.OK(devopsDiffOtaService.handleCopy(id).getMsg(),"");
    }

    /**
     * 取消排队
     *
     * @param
     * @return
     */
    @AutoLog(value = "取消排队")
    @ApiOperation(value = "取消排队", notes = "取消排队")
    @GetMapping(value = "/cancelOta")
    public Result<?> cancelOta(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        devopsDiffOtaService.cancelOta(id);
        return Result.OK("正在制作","");
    }


}
