package com.chards.committee.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author LiuSu
 * @create 2021/3/19 19:37
 */
public class UploadUtil {

//    private static final String PATH = "/field/";

    public static List<String> uploadPic(MultipartFile[] multipartFiles,String filePath){
        List<String> urlList = new ArrayList<String>();
        CreatFileDir(filePath);
        String filename = null;
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                filename = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
                multipartFile.transferTo(new File(filePath + filename));
                urlList.add(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urlList;
    }

    protected static void CreatFileDir(String filepath) {
        File dir = new File(filepath);
        if (!dir.exists())
            dir.mkdirs();
    }
}
