package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "t_patient_des")
@Data
@Accessors(chain = true)
public class PatientDes {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(length =50,name = "number")
    private String number;
    //随访时间
    @Column(length =100)
    private String followUpTimeV;
    @Column(length =100)
    private String PV;
    @Column(length =100)
    private String CaV;
    @Column(length =100)
    private String MgV;
    @Column(length =100)
    private String NaV;
    @Column(length =100)
    private String KV;
    @Column(length =100)
    private String PTHV;
    @Column(length =100)
    private String OH25DV;
    @Column(length =100)
    private String CRPV;
    @Column(length =100)
    private String FT3V;
    @Column(length =100)
    private String FT4V;
    @Column(length =100)
    private String TSHV;
    @Column(length =100)
    private String FFAV;
    @Column(length =100)
    private String ALPV;
    @Column(length =100)
    private String ALTV;
    @Column(length =100)
    private String ASTV;
    @Column(length =100)
    private String BUNV;
    @Column(length =100)
    private String CP0V;
    @Column(length =100)
    private String CP120V;
    @Column(length =100)
    private String INSOV;
    @Column(length =100)
    private String INS120V;
    @Column(length =100)
    private String ChV;
    @Column(length =100)
    private String CrV;
    @Column(length =100)
    private String FBGV;
    @Column(length =100)
    private String HDLV;
    @Column(length =100)
    private String HOMABV;
    @Column(length =100)
    private String HOMAIRV;
    @Column(length =100)
    private String HbV;
    @Column(length =100)
    private String HbA1cV;
    @Column(length =100)
    private String LDLV;
    @Column(length =100)
    private String PBGV;
    @Column(length =100)
    private String TGV;
    @Column(length =100)
    private String WBCV;
    @Column(length =100)
    private String rGTV;
    @Column(length =100)
    private String GAV;
    @Column(length =100)
    private String weightV;//体重V
    @Column(length =100)
    private String fatV; //腹内脂肪
    @Column(length =100)
    private String acidV;//尿酸V
    @Column(length =100)
    private String skinFatV;//皮下脂肪V1
    @Column(length =100)
    private String pressureV; //收缩压V1
    @Column(length =100)
    private String disPressureV;//舒张压V1
    @Column(length =100)
    private String bodyIndexV;//体重指数V1
    @Column(length =100)
    private String hiplineV; //臀围V1
    @Column(length =100)
    private String hipRatioV;//腰臀比V1
    @Column(length =100)
    private String waistV;//腰围V1

//    private List<AdviceDrug> adviceDrugs;


}
