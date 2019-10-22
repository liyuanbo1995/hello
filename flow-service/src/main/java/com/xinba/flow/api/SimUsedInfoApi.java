package com.xinba.flow.api;

import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.flow.entity.SimUsedInfo;
import com.xinba.flow.service.SimUsedInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * (SimUsedInfo)表控制层
 *
 * @author tsw
 * @since 2019-07-25 14:01:35
 */
@RestController
@RequestMapping("simUsedInfo")
public class SimUsedInfoApi {
    /**
     * 服务对象
     */
    @Resource
    private SimUsedInfoService simUsedInfoService;

    //单卡绑定套餐
    @ResponseBody
    @RequestMapping(value = "sim/binding/pack")
    public Wrapper bindPack(@PathParam("iccid")String iccid,@PathParam("ratePlan")int ratePlan){
        simUsedInfoService.bindPack(iccid,ratePlan);
        return WrapMapper.ok();
    }

}