package com.ihealthink.ks.system.webapi;


import com.ihealthink.ks.common.web.controller.BaseController;
import com.ihealthink.ks.system.common.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangwb5
 * EngineFlowInstance表相关操作
 */
@Slf4j
@Api(value = "用戶管理", tags = {"查询用户列表请求"})
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("user")
public class SysUserController extends BaseController {


    @Autowired
    private final RedisService redisService;


    @ApiOperation(value = "获取用户信息", notes = "根据条件查询用户信息列表", httpMethod = "GET")
    @GetMapping(value = "/list")
    public Map<String, Object> queryFlowInstanceList() {
        redisService.setCacheObject("aaa", "wowowoowoww");
        Map<String, Object> map = new HashMap<>();
        Object o = redisService.getCacheObject("aaa");
        map.put("aaa", o);
        return map;
    }

//    @ApiOperation(value = "根据id查询流程实例信息", notes = "根据id查询流程实例信息", httpMethod = "GET")
//    @ApiImplicitParam(name = "flowInstanceId", value = "流程实例主键", required = true, paramType = "path")
//    @GetMapping(value = "/queryFlowInstanceById/{flowInstanceId}")
//    public R<EngineFlowInstance> queryFlowInstanceById(@PathVariable("flowInstanceId") String flowInstanceId) {
//
//        return engineFlowInstanceService.queryFlowInstanceById(Long.parseLong(flowInstanceId));
//    }


}
