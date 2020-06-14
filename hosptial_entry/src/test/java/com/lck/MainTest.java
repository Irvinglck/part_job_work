package com.lck;

import java.util.HashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        String str="2021/3/5 12:35:56,,2020/4/5 12:35:56,,2020/4/5 12:35:56,,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56,2020/4/5 12:35:56\n";
        String[] afterTime = str.split(",");
        Map<String,String> resut=new HashMap<>();
        for (int i = 0; i <afterTime.length ; i++) {
            resut.put("随访时间V"+i,afterTime[i]);
        }
        resut.forEach((k,v)-> {
            System.out.println(k+"-->"+v);
        });
    }
}
