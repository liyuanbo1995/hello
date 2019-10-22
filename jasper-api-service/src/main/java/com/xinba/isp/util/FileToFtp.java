package com.xinba.isp.util;

import cn.hutool.extra.ftp.Ftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileToFtp {
    public static String uploadFile(String fileName, InputStream file){
        String ftpPath="http://ftp.simbalink.cn/upload/";
        try {
            FtpUtil.uploadToServer(file,"/upload",fileName);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ftpPath+fileName;
    }
}
