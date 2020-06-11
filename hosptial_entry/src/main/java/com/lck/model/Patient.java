package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class Patient {
    private Integer id;
    //编号
    private String number;
    private String username;
    private String gender;
    private String age;
    //是否糖尿病
    private String isDiabetes;
    private String diaAge;
    private String highBlood;
    //血脂异常
    private String boolFat;
    private String opraType;
    private String reverseOpra;
    private String isFirstOpra;
    private String opraTime;
    //住院号
    private String InhosptialNo;





























}
