package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Data
@Accessors(chain = true)
@Entity
@Table(name="t_patient")
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    //编号
    @Column(length =50)
    private String number;
    @Column(length =20)
    private String username;
    @Column(length =1)
    private String gender;
    @Column(length =3)
    private String age;
    @Column(length =1)
    //是否糖尿病
    private String isDiabetes;
    @Column(length =50)
    private String diaAge;
    @Column(length =50)
    private String highBlood;
    //血脂异常
    @Column(length =50)
    private String boolFat;
    @Column(length =50)
    private String opraType;
    @Column(length =50)
    private String reverseOpra;
    @Column(length =50)
    private String isFirstOpra;
    @Column(length =50)
    private String opraTime;
    @Column(length =50)
    private String roomId;   //住院号





























}
