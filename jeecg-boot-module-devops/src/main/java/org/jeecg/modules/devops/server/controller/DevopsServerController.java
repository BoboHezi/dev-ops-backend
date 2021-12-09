package org.jeecg.modules.devops.server.controller;

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
import org.jeecg.modules.devops.server.entity.DevopsServer;
import org.jeecg.modules.devops.server.service.IDevopsServerService;

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

import static org.jeecg.modules.devops.StatusCode.*;

/**
 * @Description: 服务器表单
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Api(tags = "服务器表单")
@RestController
@RequestMapping("/server/devopsServer")
@Slf4j
public class DevopsServerController extends JeecgController<DevopsServer, IDevopsServerService> {
    @Autowired
    private IDevopsServerService devopsServerService;

    /**
     * 分页列表查询
     *
     * @param devopsServer
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "服务器表单-分页列表查询")
    @ApiOperation(value = "服务器表单-分页列表查询", notes = "服务器表单-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DevopsServer devopsServer,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DevopsServer> queryWrapper = QueryGenerator.initQueryWrapper(devopsServer, req.getParameterMap());
        Page<DevopsServer> page = new Page<DevopsServer>(pageNo, pageSize);
        IPage<DevopsServer> pageList = devopsServerService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param devopsServer
     * @return
     */
    @AutoLog(value = "服务器表单-添加")
    @ApiOperation(value = "服务器表单-添加", notes = "服务器表单-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevopsServer devopsServer) {
        devopsServerService.save(devopsServer);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devopsServer
     * @return
     */
    @AutoLog(value = "服务器表单-编辑")
    @ApiOperation(value = "服务器表单-编辑", notes = "服务器表单-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevopsServer devopsServer) {
        devopsServerService.updateById(devopsServer);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "服务器表单-通过id删除")
    @ApiOperation(value = "服务器表单-通过id删除", notes = "服务器表单-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        devopsServerService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "服务器表单-批量删除")
    @ApiOperation(value = "服务器表单-批量删除", notes = "服务器表单-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.devopsServerService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "服务器表单-通过id查询")
    @ApiOperation(value = "服务器表单-通过id查询", notes = "服务器表单-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DevopsServer devopsServer = devopsServerService.getById(id);
        if (devopsServer == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(devopsServer);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "重启服务器-通过id查询")
    @ApiOperation(value = "重启服务器-通过id查询", notes = "重启服务器-通过id查询")
    @GetMapping(value = "/handleRestart")
    public Result<?> handleRestart(@RequestParam(name = "id", required = true) String id) {
        String message = devopsServerService.handleRestart(id);
        return Result.OK(message);
    }

    /**
     * @param
     * @return
     */
    @AutoLog(value = "重启服务器")
    @ApiOperation(value = "重启服务器", notes = "重启服务器")
    @GetMapping(value = "/handleAllRestart")
    public Result<?> handleAllRestart() {
        devopsServerService.handleAllRestart();
        return Result.OK("成功");
    }

    /**
     * @param
     * @return
     */
    @AutoLog(value = "占用释放服务器")
    @ApiOperation(value = "占用释放服务器", notes = "占用释放服务器")
    @GetMapping(value = "/handleSelect")
    public Result<?> handleSelect(@RequestParam(name = "id", required = true) String id, String status) {
        if (status.equals(SERVER_STATUS_2)) {
            status = SERVER_STATUS_0;
        } else {
            status = SERVER_STATUS_2;
        }
        devopsServerService.setStatusServer(id, status);
        return Result.OK("成功");
    }

    /**
     * @param
     * @return
     */
    @AutoLog(value = "断开连接服务器")
    @ApiOperation(value = "断开连接服务器", notes = "断开连接服务器")
    @GetMapping(value = "/breakRequestServer")
    public Result<?> breakRequestServer(@RequestParam(name = "id", required = true) String id) {
        devopsServerService.setStatusLinkServer(id, SERVER_STATUS_7,SERVER_LINK_STATUS_4,"",SERVER_LINK_TIME_START);
        devopsServerService.setStatusRefuseServer(id,SERVER_LINK_STATUS_1);
        devopsServerService.setJenkinsLinkServer(id,true);
        return Result.OK("成功");
    }

    /**
     * @param
     * @return
     */
    @AutoLog(value = "连接服务器")
    @ApiOperation(value = "连接服务器", notes = "连接服务器")
    @GetMapping(value = "/allowRequestServer")
    public Result<?> allowRequestServer(@RequestParam(name = "id", required = true) String id) {
        devopsServerService.setStatusLinkServer(id, SERVER_STATUS_7,SERVER_LINK_STATUS_3,"",SERVER_LINK_TIME_START);
        devopsServerService.setJenkinsLinkServer(id,false);
        return Result.OK("成功");
    }

    /**
     * @param
     * @return
     */
    @AutoLog(value = "拒绝请求服务器")
    @ApiOperation(value = "拒绝请求服务器", notes = "拒绝请求服务器")
    @GetMapping(value = "/refuseRequestServer")
    public Result<?> refuseRequestServer(@RequestParam(name = "id", required = true) String id) {
        devopsServerService.setStatusRefuseServer(id,SERVER_LINK_STATUS_1);
        return Result.OK("成功");
    }

    /**
     * @param
     * @return
     */
    @AutoLog(value = "jenkins 请求接口")
    @ApiOperation(value = "jenkins 请求接口", notes = "jenkins 请求接口")
    @GetMapping(value = "/jenkinsRequestServer")
    public Result<?> jenkinsRequestServer(@RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "status", required = true) String status,
            @RequestParam(name = "ServerDir", required = true) String serverDir) {
        if(status.equals(SERVER_LINK_STATUS_0)){
            devopsServerService.setStatusLinkServer(id, SERVER_STATUS_7,SERVER_LINK_STATUS_0,serverDir,SERVER_LINK_TIME_END);
        }else {
            devopsServerService.setStatusLinkServer(id, SERVER_STATUS_0,SERVER_LINK_STATUS_1,serverDir,SERVER_LINK_TIME_START);
        }
        devopsServerService.sendServerEmail(id,status,serverDir);
        return Result.OK("成功");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param devopsServer
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsServer devopsServer) {
        return super.exportXls(request, devopsServer, DevopsServer.class, "服务器表单");
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
        return super.importExcel(request, response, DevopsServer.class);
    }

}
