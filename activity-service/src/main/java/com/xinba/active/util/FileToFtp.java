package com.xinba.active.util;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class FileToFtp {
    public static String uploadFile(String fileName, InputStream file){
        String ftpPath="http://ftp.simbalink.cn/active/goods";
        try {
            FtpUtils.uploadToServer(file,"/active/goods",fileName);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ftpPath+ "/" +fileName;
    }
}


