package com.xinba.isp.api.v1;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.form.JasperRequestBody;
import com.xinba.isp.entity.form.JasperResponseBody;
import com.xinba.isp.entity.form.RequestData;
import com.xinba.isp.entity.form.Vehicles;
import com.xinba.isp.feign.GetTokenService;
import com.xinba.isp.feign.JasperVerifyService;
import com.xinba.isp.feign.RequestBodyTemplate;
import com.xinba.isp.service.VerifiedService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xinba.isp.util.UrlImageUtil.encodeImageToBase64;

/**
 * (CompanyInfo)表控制层
 *
 * @author tsw
 * @since 2019-08-20 15:05:20
 */
@RestController
@RequestMapping("/verified")
public class VerifiedApi {
    /**
     * 服务对象
     */
    @Resource
    private VerifiedService verifiedService;
    @Resource
    private JasperVerifyService jasperVerifyService;
    @Resource
    private RequestBodyTemplate requestBodyTemplate;


    //确认上传
    @RequestMapping(value = "/personal/info/submit",method = RequestMethod.GET)
    public Wrapper submitPersonalInfo(@RequestParam(value = "iccid")String iccid,
                                      @RequestParam(value = "vin",required = false)String vin){
        RequestData requestData=verifiedService.getSubmitInfo(iccid,vin);
        //处理图片Base64编码，pic1、pic2、facepic
        try {
            requestData.setPic1(encodeImageToBase64(new URL(requestData.getPic1())));
            requestData.setPic2(encodeImageToBase64(new URL(requestData.getPic2())));
            requestData.setFacepic(encodeImageToBase64(new URL(requestData.getFacepic())));
        }catch (Exception e){
            return WrapMapper.wrap(500,e.getCause().toString());
        }
        JasperRequestBody RequestBody=requestBodyTemplate.setRequestBody("personal.realNameReg",requestData);
        JasperResponseBody verifyResponseBody=new JasperResponseBody();
        try{
            verifyResponseBody=jasperVerifyService.getJasperVerify(RequestBody);
            if(verifyResponseBody.getRet_code().equals("0")){
                verifiedService.updateRetMsg(iccid,verifyResponseBody.getRet_msg(),2);
                verifiedService.insertIccidVin(iccid,vin,RequestBody.getRequest_data().getServnumber());
                return WrapMapper.ok();
            }else{
                verifiedService.updateRetMsg(iccid,verifyResponseBody.getRet_msg(),1);
                return WrapMapper.wrap(500,verifyResponseBody.getRet_msg(),verifyResponseBody.getRet_code());
            }
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.wrap(500,e.getMessage());
        }
    }

    //绑定认证回调
    @RequestMapping(value = "/personal/info/realNameRegCallback",method = RequestMethod.POST)
    public Wrapper verifyCallBack(@RequestBody RequestData requestData){
        verifiedService.updateAuditResult(requestData);
        verifiedService.updateIccidVinStatus(requestData);
        return WrapMapper.ok();
    }

    //根据iccid和vin查询是否有审核记录和审核结果
    @RequestMapping(value = "/personal/info/auditresult",method = RequestMethod.GET)
    public Wrapper getVerifyResult(@RequestParam(value = "iccid")String iccid,
                                   @RequestParam(value = "vin",required = false)String vin){
        if (iccid == null || iccid == "") {

            return WrapMapper.wrap(500,"iccid为空");
        }
        List<RequestData>verifyResult=verifiedService.queryPersonalInfo(iccid,vin,"","");
        if(verifyResult.size()==0){
            return WrapMapper.wrap(200,"无该条审核记录");
        }else if(verifyResult.get(0).getStep()==3) {
                if(verifyResult.get(0).getAuditresult()==2){
                    return WrapMapper.wrap(200,"正在审核中",verifyResult);
                }else if(verifyResult.get(0).getAuditresult()==1){
                    return WrapMapper.wrap( 200,"审核不通过",verifyResult);
                }else {
                    return WrapMapper.wrap(200,"审核通过",verifyResult);
                }
        }else {
            return WrapMapper.wrap(200,"信息不完整",verifyResult);
        }
    }

    //插入个人信息
    @RequestMapping(value = "/personal/info",method = RequestMethod.POST)
    public Wrapper savePersonalInfo(@RequestBody RequestData requestData){
        int result=verifiedService.updatePersonalInfo(requestData);
        if (result==0) {
            verifiedService.insertPersonalInfo(requestData);
        }
        return WrapMapper.ok();
    }

    //查询个人认证信息
    @RequestMapping(value = "/personal/info",method = RequestMethod.GET)
    public Wrapper getPersonalInfo(@RequestParam(value = "iccid",required = false)String iccid,
                                   @RequestParam(value = "pageNum",required = false)Integer pageNum,
                                   @RequestParam(value = "vin",required = false)String vin,
                                   @RequestParam(value = "msisdn",required = false)String msisdn,
                                   @RequestParam(value = "auditresult",required = false)String auditresult){
        Map<String, Object> map = new HashMap<>();
        int pageSize = 10;
        PageHelper.startPage(pageNum,pageSize);//分页
        PageHelper.orderBy("id desc");
        List<RequestData> personalInfoList=verifiedService.queryPersonalInfo(iccid,vin,msisdn,auditresult);
        PageInfo<RequestData> pageInfo=new PageInfo(personalInfoList,pageSize);
        map.put("items", pageInfo.getTotal());
        map.put("list",personalInfoList);
        map.put("pages",pageInfo.getPages());
        return WrapMapper.ok();
    }

    //车卡关系同步
    @RequestMapping(value = "/sync/vehicle",method = RequestMethod.GET)
    public Wrapper syncVehicle(@RequestParam(value = "iccid")String iccid,
                               @RequestParam(value = "vin")String vin,
                               @RequestParam(value = "brandid")String brand) {
        RequestData requestData=new RequestData();
        List<Vehicles>vehiclesList=new ArrayList<>();
        Vehicles vehicles=new Vehicles();
        vehicles.setVin(vin);
        vehicles.setIccid(iccid);
        vehicles.setBrand(brand);
        vehiclesList.add(vehicles);
        requestData.setVehicles(vehiclesList);
        JasperRequestBody RequestBody= requestBodyTemplate.setRequestBody("sync.vehicle",requestData);
        JasperResponseBody verifyResponseBody=new JasperResponseBody();
        try {
            verifyResponseBody = jasperVerifyService.getJasperVerify(RequestBody);
            if(verifyResponseBody.getRet_code().equals("0")){
                if(verifyResponseBody.getResponse_data().getVcbacks()[0].getRetcode()==0){
                    return WrapMapper.ok(verifyResponseBody);
                }else {
                    return WrapMapper.wrap(500,verifyResponseBody.getResponse_data().getVcbacks()[0].getMessage());
                }
            }else {
                return WrapMapper.wrap(500,verifyResponseBody.getResponse_data().getVcbacks()[0].getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.wrap(500,e.getMessage());
        }
    }


}