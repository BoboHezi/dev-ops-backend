package org.jeecg.modules.devops.compile.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.devops.compile.entity.DevopsCompile;
import org.jeecg.modules.devops.compile.service.IDevopsCompileService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.devops.entity.Messages;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

import static org.jeecg.modules.devops.StatusCode.COMPILE_STATUS_1;

/**
 * @Description: 编译管理
 * @Author: jeecg-boot
 * @Date: 2021-02-22
 * @Version: V1.0
 */
@Api(tags = "编译管理")
@RestController
@RequestMapping("/compile/devopsCompile")
@Slf4j
public class DevopsCompileController extends JeecgController<DevopsCompile, IDevopsCompileService> {
    @Autowired
    private IDevopsCompileService devopsCompileService;

    /**
     * 分页列表查询
     *
     * @param devopsCompile
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "编译管理-分页列表查询")
    @ApiOperation(value = "编译管理-分页列表查询", notes = "编译管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DevopsCompile devopsCompile,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
//		String user = JSON.toJSONString(req.getRemoteUser());
//		System.out.println(user);
        QueryWrapper<DevopsCompile> queryWrapper = QueryGenerator.initQueryWrapper(devopsCompile, req.getParameterMap());
        Page<DevopsCompile> page = new Page<DevopsCompile>(pageNo, pageSize);
        IPage<DevopsCompile> pageList = devopsCompileService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param devopsCompile
     * @return
     */
    @AutoLog(value = "编译管理-添加")
    @ApiOperation(value = "编译管理-添加", notes = "编译管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevopsCompile devopsCompile) {
        devopsCompileService.save(devopsCompile);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devopsCompile
     * @return
     */
    @AutoLog(value = "编译管理-编辑")
    @ApiOperation(value = "编译管理-编辑", notes = "编译管理-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevopsCompile devopsCompile) {
        devopsCompileService.updateById(devopsCompile);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "编译管理-通过id删除")
    @ApiOperation(value = "编译管理-通过id删除", notes = "编译管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        devopsCompileService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "编译管理-批量删除")
    @ApiOperation(value = "编译管理-批量删除", notes = "编译管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.devopsCompileService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "编译管理-通过id查询")
    @ApiOperation(value = "编译管理-通过id查询", notes = "编译管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DevopsCompile devopsCompile = devopsCompileService.getById(id);
        if (devopsCompile == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(devopsCompile);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param devopsCompile
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevopsCompile devopsCompile) {
        return super.exportXls(request, devopsCompile, DevopsCompile.class, "编译管理");
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
        return super.importExcel(request, response, DevopsCompile.class);
    }

    /**
     * 自动编译 autoCompile
     *
     * @param
     * @return
     */
    @AutoLog(value = "编译任务-自动编译")
    @ApiOperation(value = "编译任务-自动编译", notes = "编译任务-自动编译")
    @GetMapping(value = "/autoCompile")
    public Result<?> autoCompile(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        System.out.println("auto compile ... " + request);
        try {
            this.devopsCompileService.autoCompile(id);
            return Result.OK("成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.OK("失败!");
    }

    /**
     * 停止编译 stopCompile
     *
     * @param
     * @return
     */
    @AutoLog(value = "编译任务-停止编译")
    @ApiOperation(value = "编译任务-停止编译", notes = "编译任务-停止编译")
    @PostMapping(value = "/stopCompile")
    public Result<?> stopCompile(HttpServletRequest request, @RequestBody DevopsCompile devopsCompile) {
        System.out.println("stop compile ... ");
        try {
           return this.devopsCompileService.stopCompile(devopsCompile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("失败!");
    }

    /**
     * 拷贝项目
     *
     * @param
     * @return
     */
    @AutoLog(value = "拷贝项目")
    @ApiOperation(value = "拷贝项目", notes = "拷贝项目")
    @GetMapping(value = "/handleCopy")
    public Result<?> handleCopy(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        System.out.println("handleCopy ... ");
        try {
            this.devopsCompileService.handleCopy(id);
            return Result.OK("成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败！");
        }

    }

    /**
     * 取消排队
     *
     * @param
     * @return
     */
    @AutoLog(value = "取消排队")
    @ApiOperation(value = "取消排队", notes = "取消排队")
    @GetMapping(value = "/cancelCompile")
    public Result<?> cancelCompile(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        devopsCompileService.cancelCompile(id, COMPILE_STATUS_1);
        return Result.OK("取消成功！");
    }

    /**
     * 获取任务状态
     *
     * @param
     * @return
     */
    @AutoLog(value = "获取任务状态")
    @ApiOperation(value = "获取任务状态", notes = "获取任务状态")
    @GetMapping(value = "/setStatusJenkins")
    public Result<?> setStatusJenkins(HttpServletRequest request,
                                      @RequestParam(name = "id", required = true) String id,
                                      @RequestParam(name = "status", required = true) String status) {
        try {
            devopsCompileService.setCompileStatus(status, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("未找到对应数据");
        }
        return Result.OK("成功");
    }

    /**
     * 取消排队
     *
     * @param
     * @return
     */
    @AutoLog(value = "请求服务器")
    @ApiOperation(value = "请求服务器", notes = "请求服务器")
    @GetMapping(value = "/requestServer")
    public Result<?> requestServer(HttpServletRequest request, @RequestParam(name = "id", required = true) String id) {
        Messages<?> messages =  devopsCompileService.requestServer(id);
        return Result.OK(messages.getMsg(),"");
    }

}
