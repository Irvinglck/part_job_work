package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name="t_advice_drug")
public class AdviceDrug {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(length =11,name = "patient_id")
    private Integer patientId;
    @Column(length =150)
    private String InsulinQuaType;//胰岛素类（数量/种类）
    @Column(length =150)
    private String mouthDrugQuaType;//口服降糖药类（数量/种类）
    @Column(length =150)
    private String downBloodQuaType;//降压药（数量/种类）
    @Column(length =150)
    private String regulatingDrugsQuaType;//调脂类药（数量/种类）
    @Column(length =150)
    private String elseDrugs;//其它药物
}
