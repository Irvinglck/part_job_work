package com.lck;

import com.lck.model.PatientDes;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        String fileName = "templategz.zip";// 设置文件名，根据业务需要替换成要下载的文件名
        //设置文件路径
        String realPath = "D:\\lyuan\\template";
        File file = new File(realPath);
        if(!file.exists())
            file.mkdirs();
    }


}
