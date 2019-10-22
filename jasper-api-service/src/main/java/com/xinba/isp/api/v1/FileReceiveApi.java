package com.xinba.isp.api.v1;

import cn.hutool.core.date.DateUtil;
import com.xinba.common.entity.WrapMapper;
import com.xinba.common.entity.Wrapper;
import com.xinba.isp.entity.charts.ResForm;
import com.xinba.isp.entity.charts.ExpireDateForm;
import com.xinba.isp.service.IspPushDataService;
import com.xinba.isp.util.FileToFtp;
import com.xinba.isp.util.ImageCompressUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileReceiveApi {
    @Autowired
    private IspPushDataService ispPoolDataService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Wrapper receiveFile(@RequestParam(value = "file")MultipartFile file, HttpServletRequest request) throws Exception{
        if(file.isEmpty()){
            return WrapMapper.wrap(500,"文件为空");
        }

        //保存时的文件名
        String fileName = file.getOriginalFilename();  // 文件名
        String  newFileName = DateUtil.format(new Date(),"yyyyMMddHHmmss");
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String ftpFileName = newFileName+fileSuffix;
        System.out.println("________________文件大小"+file.getSize()+"__________________");
        System.out.println("________________文件大小"+file.getBytes()+"__________________");
        String resuult= FileToFtp.uploadFile(ftpFileName,ImageCompressUtil.saveMinPhoto(file.getInputStream(),1080,0.8));
        return WrapMapper.ok(resuult);
    }

    @Test
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void imagesBase64(@RequestBody ResForm list ){
        System.out.println(list);
     }


    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public Wrapper test(){
        String url="http://localhost:8083/isp/file/test";
        List<ExpireDateForm> expireDateList=ispPoolDataService.queryExpireSim();
        ispPoolDataService.pushExpireDate(expireDateList,url);
        return WrapMapper.ok();
    }

}
