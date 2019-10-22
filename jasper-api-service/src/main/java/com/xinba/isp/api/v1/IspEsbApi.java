package com.xinba.isp.api.v1;

import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.form.JasperRequestBody;
import com.xinba.isp.entity.form.JasperResponseBody;
import com.xinba.isp.entity.form.RequestData;
import com.xinba.isp.feign.GetTokenService;
import com.xinba.isp.feign.JasperVerifyService;
import com.xinba.isp.feign.RequestBodyTemplate;
import com.xinba.isp.service.IspEsbService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (TboxChangeRecord)表控制层
 *
 * @author tsw
 * @since 2019-09-09 11:25:31
 */
@RestController
@RequestMapping("/esb")
public class IspEsbApi {
    @Resource
    private IspEsbService ispEsbService;
    @Resource
    private RequestBodyTemplate requestBodyTemplate;
    @Resource
    private JasperVerifyService jasperVerifyService;

    @RequestMapping(value = "/changeTbox",method = RequestMethod.POST)
    public Wrapper tboxChange(@RequestBody RequestData requestData) {
        JasperRequestBody RequestBody = requestBodyTemplate.setRequestBody("change.tbox",requestData);
        try {
            JasperResponseBody verifyResponseBody = jasperVerifyService.getJasperVerify(RequestBody);
            if(verifyResponseBody.getRet_code()=="0") {
                ispEsbService.insertTboxChangeRecord(requestData);
                ispEsbService.updateIccidVin(requestData);
            }else {
                return WrapMapper.wrap(500,verifyResponseBody.getRet_msg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.wrap(500,e.getMessage());
        }
        return WrapMapper.ok();
    }

    //解绑认证回调
    @RequestMapping(value = "/notify/UnBind/realNameRegCallback",method = RequestMethod.POST)
    public Wrapper UnBindNotice(@RequestBody RequestData requestData) {
        ispEsbService.setVinUnBind(requestData.getIccid());
        return WrapMapper.ok();
    }

    @RequestMapping(value = "/vin/UnBind",method = RequestMethod.POST)
    public Wrapper UnBindVin(@RequestBody RequestData requestData) {
        JasperRequestBody RequestBody = requestBodyTemplate.setRequestBody("unbind.vin",requestData);
        try {
            JasperResponseBody verifyResponseBody = jasperVerifyService.getJasperVerify(RequestBody);
            if(verifyResponseBody.getRet_code()=="0") {
                return WrapMapper.ok("解绑消息已发送，等待审核");
            }else {
                return WrapMapper.wrap(500,verifyResponseBody.getRet_msg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.wrap(500,e.getMessage());
        }
    }

}