package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PatientDes {
    private Integer id;
    private Integer patientId;
    //随访时间
    private String followUpTimeV;
    private String PV;
    private String CaV;
    private String MgV;
    private String NaV;
    private String KV;
    private String PTHV;
    private String OH25DV;
    private String CRPV;
    private String FT3V;
    private String FT4V;
    private String TSHV;
    private String FFAV;
    private String ALPV;
    private String ALTV;
    private String ASTV;
    private String BUNV;
    private String CP0V;
    private String CP120V;
    private String INSOV;
    private String INS120V;
    private String ChV;
    private String CrV;
    private String FBGV;
    private String HDLV;
    private String HOMABV;
    private String HOMAIRV;
    private String HbV;
    private String HbA1cV;
    private String LDLV;
    private String PBGV;
    private String TGV;
    private String WBCV;
    private String rGTV;
    private String GAV;
    private String weightV;//体重V
    private String fatV; //腹内脂肪
    private String acidV;//尿酸V
    private String skinFatV;//皮下脂肪V1
    private String pressureV; //收缩压V1
    private String disPressureV;//舒张压V1
    private String bodyIndexV;//体重指数V1
    private String hiplineV; //臀围V1
    private String hipRatioV;//腰臀比V1
    private String waistV;//腰围V1

    private List<AdviceDrug> adviceDrugs;


}
