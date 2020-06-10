package com.lck.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdviceDrug {
    private Integer id;
    private Integer patientId;

    private String InsulinQuaType;//胰岛素类（数量/种类）
    private String mouthDrugQuaType;//口服降糖药类（数量/种类）
    private String downBloodQuaType;//降压药（数量/种类）
    private String regulatingDrugsQuaType;//调脂类药（数量/种类）
    private String elseDrugs;//其它药物
}
